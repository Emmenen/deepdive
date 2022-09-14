<template>
    <div class="recharge">
        <head-top title="交易/转账"></head-top>
        <el-form v-loading="loading" ref="form" :model="dataForm" label-width="120px">
            <el-form-item label="地址">
                <el-col :span = "12">
                    <el-input
                        v-model="accountAddress"
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

            <el-form-item label="tokenId">
              <el-col :span = "12">
                  <el-select v-model="dataForm.tokenId" placeholder="请选择">
                      <el-option
                          v-for="item in options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                          :disabled="item.disabled">
                      </el-option>
                  </el-select>
              </el-col>
              <el-col :span = "24">
              </el-col>
              <el-col :span = "9">
              </el-col>
            </el-form-item>
            <el-form-item label="释放数量">
                <el-col :span = "8">
                    <el-input
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
            <el-form-item>
              <el-button type="primary" @click="confirm">提交</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="approveTransfer=!approveTransfer" v-if="!approveTransfer">使用授权交易</el-button>
              <el-button type="primary" @click="approveTransfer=!approveTransfer" v-if="approveTransfer">取消授权交易</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import headTop from "../../components/headTop";
import VueCookies from "vue-cookies";
import {tokensOfAccount} from "../../api/getData";
import {optionsDataForm} from "../../utils/DataForm";
import {burn} from "../../api/postData";

export default {
    data() {
        return {
            options: [],
            dataForm: {
                tokenId: "",
                accountAddressFrom: "",
                amount: ""
            },
            accountAddress: "",
            loading: false,
            approveTransfer: false,
            dataChange: false,
        }
    },
    components: {
        headTop
    },
    created() {
        this.accountAddress = VueCookies.get('userInfo').address
        this.initData()
    },
    watch:{
        approveTransfer: function (newData, oldData) {
            if (!this.approveTransfer){
                this.accountAddress = VueCookies.get('userInfo').address
            }
        },
        // options: function (newData,oldData){
        //     this.dataChange = !this.dataChange;
        // }
        accountAddress: function (newData,oldData){
            this.dataForm.accountAddressFrom = newData;
            this.initData();
        }
    },
    methods: {
        async initData(){
            this.loading = true
            const res = await tokensOfAccount(this.dataForm.accountAddressFrom,0,100);
            let list = res.data.list;
            this.options = optionsDataForm(list);
            this.loading = false
        },
        async confirm(){
            this.loading = true
            const res = await burn(this.dataForm);
            if (res.status === 200){
               this.$message({
                   message: "释放完成",
                   type: "success",
                   duration: 1500
               })
            }
            this.loading = false
        }
    },
}
</script>

<style>
</style>
