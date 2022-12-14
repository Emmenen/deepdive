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
        meta: ['????????????','????????????'],
    },{
        path: '/record',
        component: record,
        meta: ['????????????','????????????'],
    },{
        path: '/recharge',
        component: recharge,
        meta: ['??????','??????'],
    },{
        path: '/transfer',
        component: transfer,
        meta: ['??????','??????'],
    },{
        path: '/evaluate',
        component: evaluate,
        meta: ['????????????','??????'],
    },{
        path: '/transferRecord',
        component: transferRecord,
        meta: ['????????????', '????????????'],
    },{
        path: '/addGoods',
        component: addGoods,
        meta: ['????????????', '????????????'],
    },{
        path: '/coinList',
        component: coinList,
        meta: ['????????????', '??????']
    },{
        path: '/nftList',
        component: nftList,
        meta: ['????????????', '????????????'],
    },{
        path: '/mint',
        component: mint,
        meta: ['????????????', '????????????'],
    },{
        path: '/orderList',
        component: orderList,
        meta: ['????????????', '????????????'],
    },{
        path: '/adminList',
        component: adminList,
        meta: ['????????????', '???????????????'],
    },{
        path: '/visitor',
        component: visitor,
        meta: ['??????', '????????????'],
    },{
        path: '/newMember',
        component: newMember,
        meta: ['??????', '????????????'],
    },{
        path: '/uploadImg',
        component: uploadImg,
        meta: ['????????????', 'MarkDown'],
    },{
        path: '/vueEdit',
        component: vueEdit,
        meta: ['??????', '????????????'],
    },{
        path: '/adminSet',
        component: adminSet,
        meta: ['??????', '???????????????'],
    },{
        path: '/sendMessage',
        component: sendMessage,
        meta: ['??????', '????????????'],
    },{
        path: '/explain',
        component: explain,
        meta: ['??????', '??????'],
    },{
        path: "/tokenDetail",
        component: tokenDetail,
        meta: ['token', '??????????????????']
    },{
        path: "/burn",
        component: burn,
        meta: ['token', '??????']
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
 * ????????????????????????, global: ????????????, main: ???????????????
 * @param {*} route ????????????
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
