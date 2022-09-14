<template>
    <div>
        <head-top></head-top>
        <el-row style="margin-top: 20px;" v-loading="loading">

            <el-col :span="12" :offset="4">

                <el-form :model="formData" :rules="rules" ref="formData" label-width="110px" class="demo-formData">
                    <div v-if="ifUploadMetaData">
                        <el-form-item label="gsId" v-if="verified" :prop="metaData.gsId">
                            <el-input :disabled="verified" v-model="metaData.gsId"></el-input>
                        </el-form-item>
                        <el-form-item label="projectID" v-if="verified" :prop="metaData.projectId">
                            <el-input  :disabled="verified" v-model="metaData.projectId"></el-input>
                        </el-form-item>
                        <el-form-item label="creditID"  :prop="metaData.creditId" required>
                            <el-input :disabled="verified" v-model="metaData.creditId"></el-input>
                        </el-form-item>
                        <el-form-item  label="发行日期" v-if="verified" :prop="metaData.publishDate">
                            <el-input :disabled="verified" v-model="metaData.publishDate"></el-input>
                        </el-form-item>
<!--                        <el-form-item label="图片" :prop="metaData.image">-->
<!--                            <el-upload-->
<!--                                class="upload-demo"-->
<!--                                drag-->
<!--                                limit="1"-->
<!--                                :on-success="uploadMetaData"-->
<!--                                action="#"-->
<!--                                :before-upload="uploadImage"-->
<!--                                :file-list="fileList"-->
<!--                                multiple>-->
<!--                                <i class="el-icon-upload"></i>-->
<!--                                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>-->
<!--                                <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>-->
<!--                            </el-upload>-->
<!--                        </el-form-item>-->
                    </div>
                    <el-form-item label="数据链接" prop="metaDataURI" >
                        <el-row>
                            <el-col :span="20">
                                <el-input v-model="formData.metaDataURI" :disabled="ifUploadMetaData"></el-input>
                            </el-col>
                            <el-col :span="4">
                                <el-button type="primary"  v-if="!verified" plain @click="ifUploadMetaData=!ifUploadMetaData">切换</el-button>
                            </el-col>
                        </el-row>
                    </el-form-item>
                    <el-form-item label="数量" v-if="verified" prop="amount">
                        <el-input v-model="formData.amount" :disabled="verified"></el-input>
                    </el-form-item>
                    <el-form-item class="button_submit">
                        <el-button type="primary" :disabled="!verified" @click="submitForm()">立即创建</el-button>
                        <el-button type="primary" @click="verify()" :disabled="verified" >验证</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import headTop from '@/components/headTop'
import {mintNewNFT,getCreditByCreditId} from "@/api/getData";
import {uploadImageFile,uploadMetaData} from "@/api/postData"
export default {
    data(){
        return {
            fileList:[],
            loading: false,
            ifUploadMetaData: false,
            verified: false,
            metaData: {
                gsId: "",
                creditId: "",
                publishDate: "",
                projectId: "",
            },
            formData: {
                amount: 0,
                metaDataURI: "",
            },
            rules: {
                name: [{
                    required: false,
                    message: '请输入名称',
                    trigger: 'blur'
                }],
                description: [{
                    required: false,
                    message: '请输入描述',
                    trigger: 'blur'
                }],
                metaDataURI: [{
                    required: true,
                    message: '请输入数据连接',
                    trigger: 'blur'
                }],
                // amount: [{
                //     required: true,
                //     message: '请输入数量',
                //     trigger: 'blur'
                // }, {
                //     pattern: /^(([1-9]\d+)|([2-9]))$/,
                //     message: '请输入正整数',
                //     trigger: 'blur'
                // }],
            },

        }
    },
    components: {
        headTop,
    },
    mounted(){
    },
    methods: {
        async submitForm() {
            if (this.formData.metaDataURI===""){
                this.$message({
                    message: "请先输入数据链接",
                    type: "warning",
                    duration: 1500,
                })
                return
            }
            this.loading = true;
            const res = await mintNewNFT(this.formData.metaDataURI,this.formData.amount);
            if (res.status === 200)
            {
                this.$message({
                    message: "铸造成功" + res.data,
                    type:"success",
                    duration: 1500,
                })
            }
            else {
                this.$message({
                    message: "铸造失败：" + res.message,
                    type:"error",
                    duration: 1500,
                })
            }
            this.loading = false;
        },
        async uploadMetaData(data){
            let res = await uploadMetaData(this.metaData);
            return res;
        },
        async uploadImage(file){
            const newVar = await uploadImageFile(file);
            console.log("newVar=>",newVar)
            this.metaData.image = newVar.data.url;
            this.fileList.push(newVar.data)
            await this.inputChange()
            return false
        },
        // async inputChange(){
        //     let newVar = null
        //     console.log("inputChangebefore")
        //     console.log("this.metaData=>",this.metaData)
        //     if (this.metaData.creditId!==""&&this.metaData.gsId!==""&&this.metaData.projectId!==""){
        //         newVar = await uploadMetaData(this.metaData);
        //         console.log("newVar=>",newVar)
        //         this.formData.metaDataURI  = newVar.data.url;
        //     }
        //     else {
        //         return
        //     }
        // },
        async verify(){
            if (this.metaData.creditId!==""){
                this.loading = true
                const res = await getCreditByCreditId(this.metaData.creditId);
                console.log("res=>",res)
                if (res.status===200){
                    this.verified = true;
                    let credit = res.data;
                    console.log("credit=>",credit)
                    this.metaData.gsId = credit.gsId.toString()
                    this.metaData.projectId = credit.projectId.toString();
                    this.metaData.publishDate = credit.createdAt;
                    this.formData.amount = credit.numberOfCredits;
                    let res2 = await this.uploadMetaData()
                    console.log("res2=>",res2)
                    this.formData.metaDataURI = res2.data.url;
                    this.$message({
                        message: "CreditId验证通过",
                        type: "success",
                        duration: 1500,
                    })
                }else {
                    this.$message({
                        message: res.message,
                        type: "error",
                        duration: 1500,
                    })
                }
                this.loading = false

            }
        }
    }
}
</script>

<style lang="less">
@import '../style/mixin';
.button_submit{
    text-align: center;
}
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}
.avatar-uploader .el-upload:hover {
    border-color: #20a0ff;
}
.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 120px;
    height: 120px;
    line-height: 120px;
    text-align: center;
}
.avatar {
    width: 120px;
    height: 120px;
    display: block;
}
.el-table .info-row {
    background: #c9e5f5;
}

.el-table .positive-row {
    background: #e2f0e4;
}
</style>
