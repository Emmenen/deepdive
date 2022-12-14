import Vue from 'vue'
import Vuex from 'vuex'
import {getAdminInfo} from '@/api/getData'
import user from './modules/user'

Vue.use(Vuex)

const state = {
	adminInfo: {
		avatar: 'default.jpg',
	},

}

const mutations = {
	// saveAdminInfo(state, adminInfo){
	// 	state.adminInfo = adminInfo;
	// }
}

const actions = {
	async getAdminData({commit}){
		// try{
		// 	const res = await getAdminInfo()
		// 	if (res.status == 1) {
		// 		commit('saveAdminInfo', res.data);
		// 	}else{
		// 		throw new Error(res.type)
		// 	}
		// }catch(err){
		// 	// console.log(err)
		// }
	},
    async getCurrentUserInfo({commit}){
        state.currentUserInfo =  window.SITE_CONFIG['userInfo']
    }
}

export default new Vuex.Store({
    modules: {
        user
    },
	state,
	actions,
	mutations,
})
