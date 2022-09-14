import Vue from 'vue'
import Router from 'vue-router'
import VueCookies from 'vue-cookies'
Vue.use(Router)

const login = r => require.ensure([], () => r(require('@/page/login')), 'login');
const s404 = r => require.ensure([], () => r(require('@/page/common/404')), '404');
const certFound = r => require.ensure([], () => r(require('@/page/certFound')), 'certFound');
// const manage = r => require.ensure([], () => r(require('@/page/manage')), 'manage');
// const home = r => require.ensure([], () => r(require('@/page/home')), 'home');
const myWallet = r => require.ensure([], () => r(require('@/page/myWallet')), 'myWallet');
const recharge = r => require.ensure([], () => r(require('@/page/recharge')), 'recharge');
const record = r => require.ensure([], () => r(require('@/page/record')), 'record');
const transfer = r => require.ensure([], () => r(require('@/page/transfer')), 'transfer');
const burn = r => require.ensure([], () => r(require('@/page/transfer/burn')), 'burn');
const register = r => require.ensure([], () => r(require('@/page/register')), 'register');
const evaluate = r => require.ensure([], () => r(require('@/page/customer/evaluate')), 'evaluate');

const manage = r => require.ensure([], () => r(require('@/page/manage')), 'manage');
const home = r => require.ensure([], () => r(require('@/page/home')), 'home');
const transferRecord = r => require.ensure([], () => r(require('@/page/transferRecord')), 'transferRecord');
const addGoods = r => require.ensure([], () => r(require('@/page/addGoods')), 'addGoods');
const coinList = r => require.ensure([], () => r(require('@/page/coinList')), 'coinList');
const nftList = r => require.ensure([], () => r(require('@/page/nftList')), 'nftList');
const tokenDetail = r => require.ensure([], () => r(require('@/page/token/tokenDetail')), 'tokenDetail');
const mint = r => require.ensure([], () => r(require('@/page/mint')), 'mint');
const orderList = r => require.ensure([], () => r(require('@/page/orderList')), 'orderList');
const adminList = r => require.ensure([], () => r(require('@/page/adminList')), 'adminList');
const visitor = r => require.ensure([], () => r(require('@/page/visitor')), 'visitor');
const newMember = r => require.ensure([], () => r(require('@/page/newMember')), 'newMember');
const uploadImg = r => require.ensure([], () => r(require('@/page/uploadImg')), 'uploadImg');
const vueEdit = r => require.ensure([], () => r(require('@/page/vueEdit')), 'vueEdit');
const adminSet = r => require.ensure([], () => r(require('@/page/adminSet')), 'adminSet');
const sendMessage = r => require.ensure([], () => r(require('@/page/sendMessage')), 'sendMessage');
const explain = r => require.ensure([], () => r(require('@/page/explain')), 'explain');
const globalRoutes = [
    {
        path: '/',
        component: login
    },{
        path: '/register',
        component: register,
    },{
        path: '*',
        component: s404,
    },{
        path: '/certFound',
        component: certFound,
    }
]

const routes = [{
    path: '/manage',
    component: manage,
    name: '',
    children: [{
        path: '',
        component: home,
        meta: [],
    },{
        path: '/myWallet',
        component: myWallet,
        meta: ['数据管理','我的钱包'],
    },{
        path: '/record',
        component: record,
        meta: ['数据管理','交易记录'],
    },{
        path: '/recharge',
        component: recharge,
        meta: ['交易','充值'],
    },{
        path: '/transfer',
        component: transfer,
        meta: ['交易','转账'],
    },{
        path: '/evaluate',
        component: evaluate,
        meta: ['顾客操作','评价'],
    },{
        path: '/transferRecord',
        component: transferRecord,
        meta: ['添加数据', '添加商铺'],
    },{
        path: '/addGoods',
        component: addGoods,
        meta: ['添加数据', '添加商品'],
    },{
        path: '/coinList',
        component: coinList,
        meta: ['数据管理', '仓库']
    },{
        path: '/nftList',
        component: nftList,
        meta: ['数据管理', '商家列表'],
    },{
        path: '/mint',
        component: mint,
        meta: ['数据管理', '食品列表'],
    },{
        path: '/orderList',
        component: orderList,
        meta: ['数据管理', '订单列表'],
    },{
        path: '/adminList',
        component: adminList,
        meta: ['数据管理', '管理员列表'],
    },{
        path: '/visitor',
        component: visitor,
        meta: ['图表', '用户分布'],
    },{
        path: '/newMember',
        component: newMember,
        meta: ['图表', '用户数据'],
    },{
        path: '/uploadImg',
        component: uploadImg,
        meta: ['文本编辑', 'MarkDown'],
    },{
        path: '/vueEdit',
        component: vueEdit,
        meta: ['编辑', '文本编辑'],
    },{
        path: '/adminSet',
        component: adminSet,
        meta: ['设置', '管理员设置'],
    },{
        path: '/sendMessage',
        component: sendMessage,
        meta: ['设置', '发送通知'],
    },{
        path: '/explain',
        component: explain,
        meta: ['说明', '说明'],
    },{
        path: "/tokenDetail",
        component: tokenDetail,
        meta: ['token', '数字藏品详情']
    },{
        path: "/burn",
        component: burn,
        meta: ['token', '释放']
    }]
}]

const router =  new Router({
    routes: globalRoutes.concat(routes),
    strict: process.env.NODE_ENV !== 'production',
})

router.beforeEach((to, from, next)=>{
    if(VueCookies.get('userInfo') || fnCurrentRouteType(to,globalRoutes) === 'global'){
        next()
    }else{
        router.push("/")
    }
})

/**
 * 判断当前路由类型, global: 全局路由, main: 主入口路由
 * @param {*} route 当前路由
 */
function fnCurrentRouteType (route, globalRoutes = []) {
    var temp = []
    for (var i = 0; i < globalRoutes.length; i++) {
        if (route.path === globalRoutes[i].path) {
            return 'global'
        } else if (globalRoutes[i].children && globalRoutes[i].children.length >= 1) {
            temp = temp.concat(globalRoutes[i].children)
        }
    }
    return temp.length >= 1 ? fnCurrentRouteType(route, temp) : 'main'
}

export default router
