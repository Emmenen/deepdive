<template>
    <div class="recharge">
        <head-top title="交易/转账"></head-top>
        <el-form v-loading="loading" ref="form" :model="dataForm" label-width="120px">

            <el-form-item label="转账地址">
              <el-col :span = "12">
                  <el-input
                  @change="inputAddress"
                  v-model="dataForm.addressForm"
                  placeholder="dataForm.addressForm"
                  :maxlength="50"
                  :disabled="!approveTransfer"
                  show-word-limit></el-input>
              </el-col>
              <el-col :span = "24">
              </el-col>
              <el-col :span = "9">
              </el-col>
            </el-form-item>
            <el-form-item label="转账目标地址">
              <el-col :span = "12">
                  <el-input
                  @change="inputAddress"
                  v-model="dataForm.addressTo"
                  placeholder="Please input address"
                  :maxlength="50"
                  show-word-limit></el-input>
              </el-col>
              <el-col :span = "24">
              </el-col>
              <el-col :span = "9">
              </el-col>
            </el-form-item>
            <el-form-item label="转账数量">
                <el-col :span = "8">
                    <el-input
                        @change="inputchange"
                        v-model="dataForm.amount"
                        placeholder="Please input amount"
                        :maxlength="6"
                        show-word-limit></el-input>
                </el-col>
                <el-col :span = "24">
                </el-col>
                <el-col :span = "9">
                </el-col>
            </el-form-item>
            <el-form-item label="tokenId">
                <el-col :span = "8">
                    <el-input
                        @change="inputchange"
                        v-model="dataForm.tokenId"
                        placeholder="Please input amount"
                        :maxlength="6"
                        show-word-limit></el-input>
                </el-col>
                <el-col :span = "24">
                </el-col>
                <el-col :span = "9">
                </el-col>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="confirm">提交</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="approveTransfer=!approveTransfer">使用授权交易</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import headTop from "../components/headTop";
import {safeTransferFrom} from "@/api/getData";
import VueCookies from "vue-cookies";

export default {
    data() {
        return {
            approveTransfer: false,
            dataForm:{
                tokenId: 0,
                amount: 0,
                addressForm: "",
                addressTo: "",
            },
            loading: false
        }
    },
    created() {
        this.dataForm.addressForm = VueCookies.get('userInfo').address
    },
    methods: {
       async confirm(){
            this.loading = true
            // if(!this.inputcheck(this.dataForm.amount)){
            //     this.loading = false
            //     return
            // }
            // if(!this.inputcheck(this.dataForm.addressTo)){
            //     this.loading = false
            //     return
            // }
            const res = await safeTransferFrom(this.dataForm.addressForm,this.dataForm.addressTo,this.dataForm.tokenId,this.dataForm.amount);
            if (res.status === 200){
                this.$message({
                    message: res.message+":"+ res.data,
                    type:"success",
                    duration: 1500,
                })
            }else {
                this.$message({
                    message: "交易失败：" + res.message,
                    type:"error",
                    duration: 1500,
                })
            }
           this.loading = false
       },
        inputchange(value){
            this.inputcheck(value)
        },
        inputAddress(value){
            this.addressCheck(value)
        },
        inputcheck(value){
            // var numberExp = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9](0-9)?$)/
            // if(!numberExp.test(value)){
            //         this.$message({
            //             type: "error",
            //             message: "请输入正确的金额"
            //         })
            //     return false
            // }
            // if (value === 0){
            //     this.$message({
            //         type: "error",
            //         message: "请输入正确的金额"
            //     })
            //     return false
            // }
            // return true

        },
        addressCheck(value){
            // var numberExp = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9](0-9)?$)/
            // if(!numberExp.test(value)||value.length!=48){
            //         this.$message({
            //             type: "error",
            //             message: "请输入正确的地址"
            //         })
            //     return false
            // }
            return true
        },
    },
    components: {
        headTop
    }
}
</script>

<style>
</style>
