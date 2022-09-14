<template>
  	<div class="login_page fillcontain">
	  	<transition name="form-fade" mode="in-out">
	  		<section class="form_contianer" v-show="showLogin">
		  		<div class="manage_tip">
		  			<p>elm后台管理系统</p>
		  		</div>
		    	<el-form :model="loginForm" :rules="rules" ref="loginForm" v-loading="loading">
					<el-form-item prop="username">
                        <el-col :span="4">
                            <span>用户名</span>
                        </el-col>
                        <el-col :span="19" :offset="1">
                            <el-input v-model="loginForm.username" placeholder="用户名"><span></span></el-input>
                        </el-col>
					</el-form-item>
                    <el-form-item prop="orgName">
                        <el-col :span="4">
                            <span>组织</span>
                        </el-col>
                        <el-col :span="20" :offset="0">
                            <el-select v-model="loginForm.orgName" placeholder="请选择组织">
                                <el-option
                                    v-for="item in orgOption"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-col>
                    </el-form-item>
                    <div v-if="loginForm.orgName == 'ORG2'">
                        <el-form-item prop="username" >
                            <el-col :span="4">
                                <span>店铺名称</span>
                            </el-col>
                            <el-col :span="19" :offset="1">
                                <el-input suffix-icon="el-icon-date"  v-model="loginForm.storeName" placeholder="店铺名称"><span></span></el-input>
                            </el-col>
                        </el-form-item><el-form-item prop="username" >
                            <el-col :span="4">
                                <span>地址</span>
                            </el-col>
                            <el-col :span="19" :offset="1">
                                <el-input suffix-icon="el-icon-date"  v-model="loginForm.address" placeholder="地址"><span></span></el-input>
                            </el-col>
                        </el-form-item>
                    </div>

					<el-form-item prop="password">
                        <el-col :span="4">
                            <span>密码</span>
                        </el-col>
                        <el-col :span="19" offset="1">
                            <el-input type="password" placeholder="密码" v-model="loginForm.password"></el-input>
                        </el-col>
					</el-form-item>
					<el-form-item prop="password2">
                        <el-col :span="4">
                            <span>确认密码</span>
                        </el-col>
                        <el-col :span="19" :offset="1">
                            <el-input type="password" placeholder="确认密码" v-model="loginForm.password2"></el-input>
                        </el-col>
					</el-form-item>
					<el-form-item>
				    	<el-button type="primary" @click="submitForm" class="submit_btn">提交</el-button>
				  	</el-form-item>
				</el-form>
	  		</section>
	  	</transition>
  	</div>
</template>

<script>
	import {login, getAdminInfo} from '@/api/getData'
	import {mapActions, mapState} from 'vuex'
    import {organization} from "../store/organization";
	export default {
	    data(){
			return {
                loading: false,
				loginForm: {
					username: '',
                    storeName:'',
                    address: '',
                    orgName:'',
					password: '',
                    password2:'',
				},
				rules: {
					username: [
			            { required: true, message: '请输入用户名', trigger: 'blur' },
			        ],
                    address: [
			            { required: true, message: '请输入地址', trigger: 'blur' },
			        ],
					password: [
						{ required: true, message: '请输入密码', trigger: 'blur' }
					],
					password2: [
						{ required: true, message: '请确认密码', trigger: 'blur' }
					],
					orgName: [
						{ required: true, message: '请输入组织名', trigger: 'blur' }
					],
				},
				showLogin: true,
                orgOption:organization
			}
		},

		computed: {
		},
		methods: {
			submitForm() {
                if(this.loginForm.password != this.loginForm.password2){
                    this.$message({
                        type: "error",
                        message: "两次输入密码不同"
                    })
                    return
                }
                this.loading = true
                this.loginForm.address=[1.000,2.000]
				this.$http({
				    url: this.$http.adornUrl("/register"),
				    method: "post",
				    data: this.loginForm
				}).then((res)=>{
                    console.log(res)
				    this.loading = false
				    this.datalist = res.data
                    this.$message({
                        type: "success",
                        message: "注册成功！"
                    })
                    this.$router.push("/")
				})
                this.loading = false
			},
		},
		watch: {
		}
	}
</script>

<style lang="less" scoped>
	@import '../style/mixin';
	.login_page{
		background-color: #324057;
	}
	.manage_tip{
		position: absolute;
		width: 100%;
		top: -100px;
		left: 0;
		p{
			font-size: 34px;
			color: #fff;
		}
	}
	.form_contianer{
        .wh(360px, auto());
        .ctp(320px, 210px);
		padding: 25px;
		border-radius: 5px;
		text-align: center;
		background-color: #fff;
		.submit_btn{
			width: 100%;
			font-size: 16px;
		}
	}
	.tip{
		font-size: 12px;
		color: red;
	}
	.form-fade-enter-active, .form-fade-leave-active {
	  	transition: all 1s;
	}
	.form-fade-enter, .form-fade-leave-active {
	  	transform: translate3d(0, -50px, 0);
	  	opacity: 0;
	}
</style>
