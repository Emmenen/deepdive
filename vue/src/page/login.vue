<template>
  	<div class="login_page fillcontain">
	  	<transition name="form-fade" mode="in-out">
	  		<section class="form_contianer" v-show="showLogin">
		  		<div class="manage_tip">
		  			<p>XXX</p>
		  		</div>
		    	<el-form v-loading="loading" :model="loginForm" :rules="rules" ref="loginForm">
					<el-form-item prop="username" v-if="!privateKeyLogin" >
                        <el-col :span="4">
                            <span>用户名</span>
                        </el-col>
                        <el-col :span="19" :offset="1">
                            <el-input  v-model="loginForm.username" placeholder="用户名"><span></span></el-input>
                        </el-col>
					</el-form-item>
					<el-form-item prop="password" v-if="!privateKeyLogin">
                        <el-col :span="4">
                            <span>密码</span>
                        </el-col>
                        <el-col :span="19" :offset="1">
                            <el-input type="password" placeholder="密码" v-model="loginForm.password"></el-input>
                        </el-col>
					</el-form-item>
                    <el-form-item label="导入私钥" prop="privateKey" v-if="privateKeyLogin">
                        <el-input v-model="loginForm.privateKey" type="textarea" placeholder="请在此填写私钥" show-word-limit
                                  :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
                    </el-form-item>

<!--                    <span class="certLost" @click="certLost" >证书丢失？</span>-->
				</el-form>
                <el-row>
                    <el-col :span="22" offset="1">
                        <el-button type="primary" @click="login" class="login_button" >登录</el-button>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="22" offset="1">
                        <el-button type="primary" @click="privateKeyLogin=!privateKeyLogin"  class="login_button"  >切换私钥登录</el-button>
                    </el-col>
                </el-row>
<!--                <el-row>-->
<!--                    <el-col :span="10" offset="13">-->
<!--                        <el-button type="primary" @click="register"  class="login_button"  >注册</el-button>-->
<!--                    </el-col>-->
<!--                </el-row>-->

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
				loginForm: {
					username: '',
					password: '',
                    privateKey: '',
				},

				rules: {
					username: [
			            { required: true, message: '请输入用户名', trigger: 'blur' },
			        ],
					password: [
						{ required: true, message: '请输入密码', trigger: 'blur' }
					],
                    privateKey: [
						{ required: true, message: '请输入私钥', trigger: 'blur' }
					],
				},
                privateKeyLogin: false,
				showLogin: false,
                orgOption:organization,
                loading: false,
			}
		},
		mounted(){
            this.showLogin = true
            if(this.$cookies.isKey('userInfo')){
                this.$router.push('manage')
            }
		},
		computed: {
			...mapState(['adminInfo']),
			...mapState(['currentUserInfo']),
		},
		methods: {
			...mapActions(['getAdminData']),
			async submitForm(formName) {
				this.$refs[formName].validate(async (valid) => {
					if (valid) {
						const res = await login({user_name: this.loginForm.username, password: this.loginForm.password})
						if (res.status == 1) {
							this.$message({
		                        type: 'success',
		                        message: '登录成功'
		                    });
							this.$router.push('manage')
						}else{
							this.$message({
		                        type: 'error',
		                        message: res.message
		                    });
						}
					} else {
						this.$notify.error({
							title: '错误',
							message: '请输入正确的用户名密码',
							offset: 100
						});
						return false;
					}
				});
			},
            login(){
                this.loading = true
                this.$http({
                    url: this.$http.adornUrl("/login"),
                    method: "post",
                    data: this.loginForm
                }).then((res)=>{
                    console.log(res)
                    this.loading = false
                    if (!res.data.token){
                        this.$message({
                            type: 'error',
                            message: '账号或密码错误！'
                        });
                        return
                    }
                    this.$cookies.set('Authorization', res.data.token)
                    var userInfo = {
                        "userName": "私钥用户",
                        "orgName": this.loginForm.orgName,
                        "address": res.data.address
                    }
                    this.$cookies.set('userInfo',userInfo)
                    this.$store.state.user=userInfo
                    window.SITE_CONFIG['userInfo'] = userInfo
                    this.$router.push('manage')
                }).catch((e)=>{
                    this.$message({
                        type: 'error',
                        message: e
                    });
                    this.loading = false
                })
            },
            register(){
                this.$router.push('register')
            },
            certLost(){
                console.log("123")
                this.$router.push('certFound')
            }

        },
		watch: {
			adminInfo: function (newValue){
				if (newValue.id) {
					this.$message({
                        type: 'success',
                        message: '检测到您之前登录过，将自动登录'
                    });
					this.$router.push('manage')
				}
			},
            userInfo: (newValue)=>{
                if(!newValue){
                    this.$message({
                        type: 'success',
                        message: '检测到您之前登录过，将自动登录'
                    });
                    this.$router.push('manage')
                }
            }
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
    .login_button{
        .wh(100%, 100%);
        margin-top: 4px;
    }
	.form_contianer{
		.wh(400px,  auto());
		.ctp(320px, 210px);
		padding: 25px;
		border-radius: 5px;
		text-align: center;
		background-color: #fff;
		.submit_btn{
			width: 50%;
			font-size: 16px;
		}
	}
	.tip{
		font-size: 12px;
		color: red;
	}
    .certLost{
        float: right;
        font-size: 14px;
        cursor:pointer;
    }
	.form-fade-enter-active, .form-fade-leave-active {
	  	transition: all 1s;
	}
	.form-fade-enter, .form-fade-leave-active {
	  	transform: translate3d(0, -50px, 0);
	  	opacity: 0;
	}
</style>
