<template>
  	<div class="login_page fillcontain">
	  	<transition name="form-fade" mode="in-out">
	  		<section class="form_contianer" v-show="showLogin">
		  		<div class="manage_tip">
		  			<p>找回证书</p>
		  		</div>
		    	<el-form v-loading="loading" :model="certForm" :rules="rules" ref="loginForm">
                    <el-form-item prop="orgName">
                        <el-col :span="4">
                            <span>组织</span>
                        </el-col>
                        <el-col :span="19" :offset="1">
                            <el-select v-model="certForm.org" placeholder="请选择组织">
                                <el-option
                                    v-for="item in orgOption"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-col>
                    </el-form-item>

                    <el-form-item prop="username">
                        <el-col :span="4">
                            <span>用户名</span>
                        </el-col>
                        <el-col :span="19" :offset="1">
                            <el-input  v-model="certForm.username" placeholder="要找回的用户(非必填)"><span></span></el-input>
                        </el-col>
                    </el-form-item>
<!--                    <el-form-item prop="password">-->
<!--                        <el-col :span="4">-->
<!--                            <span>密码</span>-->
<!--                        </el-col>-->
<!--                        <el-col :span="19" :offset="1">-->
<!--                            <el-input type="password" placeholder="密码" v-model="certForm.password"></el-input>-->
<!--                        </el-col>-->
<!--                    </el-form-item>-->
					<el-form-item prop="privateKey">
                        <el-col :span="4">
                            <span>私钥</span>
                        </el-col>
                        <el-col :span="19" :offset="1">
                            <el-input disabled  v-model="certForm.privateKey" placeholder="私钥"><span></span></el-input>
                        </el-col>
					</el-form-item>
					<el-form-item prop="publicKeyX">
                        <el-col :span="4">
                            <span>公钥X</span>
                        </el-col>
                        <el-col :span="19" :offset="1">
                            <el-input disabled  placeholder="公钥X" v-model="certForm.publicKeyX"></el-input>
                        </el-col>
					</el-form-item>
                    <el-form-item prop="publicKeyY">
                        <el-col :span="4">
                            <span>公钥Y</span>
                        </el-col>
                        <el-col :span="19" :offset="1">
                            <el-input disabled  placeholder="公钥Y" v-model="certForm.publicKeyY"></el-input>
                        </el-col>
                    </el-form-item>
					<el-form-item>
                        <el-col :span="22" offset="1">
                            <el-button type="primary" @click="submit" class="login_button" >提交</el-button>
                        </el-col>
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
				certForm: {
					privateKey: '',
                    publicKeyX: '',
					publicKeyY: '',
                    username: '',
                    org: '',
                    password: '',
				},
				rules: {
					org: [
			            { required: true, message: '请选择组织', trigger: 'blur' },
			        ],
                    privateKey: [
			            { required: false, message: '请输入私钥', trigger: 'blur' },
			        ],
					publicKeyX: [
						{ required: false, message: '请输入公钥X', trigger: 'blur' }
					],
					publicKeyY: [
						{ required: false, message: '请输入公钥Y', trigger: 'blur' }
					],
				},
				showLogin: false,
                orgOption: [{
                    label:"agent",
                    value:"ORG1"
                },{
                    label:"store",
                    value:"ORG2"
                }],
                loading: false,
			}
		},
		mounted(){
            this.showLogin = true
		},
		computed: {
		},
		methods: {
            submit(){
                this.loading = true
                console.log(this.certForm)
                this.$http({
                    url: this.$http.adornUrl("/getCert"),
                    method: "post",
                    data: this.certForm,
                    responseType: 'blob'
                }).then((res)=>{
                    this.loading = false
                    this.downloadFn(res.data)
                }).catch((e)=>{
                    this.$message({
                        type: 'error',
                        message: e
                    });
                    this.loading = false
                })
            },
            downloadFn(flow = null) {
                if (!flow.size<0) return
                const blob = new Blob([flow])
                const blobUrl = window.URL.createObjectURL(blob)
                const a = document.createElement('a')
                a.style.display = 'none'
                var nowDate = new Date();
                let date = nowDate.getFullYear()+'-'+(nowDate.getMonth() + 1)+'-'+nowDate.getDate()
                a.download = this.certForm.username+this.certForm.org+date+'证书列表.xlsx' // 自定义下载的文件名
                a.href = blobUrl
                a.click()
            }

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
    .login_button{
        .wh(100%, 100%);
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
    }
	.form-fade-enter-active, .form-fade-leave-active {
	  	transition: all 1s;
	}
	.form-fade-enter, .form-fade-leave-active {
	  	transform: translate3d(0, -50px, 0);
	  	opacity: 0;
	}
</style>
