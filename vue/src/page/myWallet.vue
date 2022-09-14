<template>
    <div class="mywallet">
        <head-top title="数据管理/我的钱包"></head-top>

        <el-card class="box-card" v-loading="loading">
            <template #header>
              <div class="card-header">
                <span>钱包信息</span>
                <!-- <el-button class="button" type="text">Operation button</el-button> -->
              </div>
            </template>
            <el-row>
                <el-col :span="24">
                    <el-tag>钱包地址</el-tag>
                    <el-tag type="success">{{datalist.serialNumberA}}</el-tag>
                </el-col>
                <el-col :span="24">
                    <el-tag >钱包余额</el-tag>
                    <el-tag type="success">{{datalist.account}}</el-tag>
                </el-col>
                <el-button @click="getData()">刷新</el-button>
            </el-row>
          </el-card>
    </div>
</template>

<script>
import HeadTop from "../components/headTop";
export default {
    data() {
        return {
            datalist: {
                serialNumber: null,
                account: 0,
            },
            loading: false
        }
    },
    mounted(){
        this.getData()
    },
    methods: {
        getData(){
            this.loading = true
            console.log(this.$http.adornData())
            this.$http({
                url: this.$http.adornUrl("/getAccount"),
                method: "post",
                data: this.$http.adornData()
            }).then((res)=>{
                this.loading = false
                this.datalist = res.data
            })
            this.loading = false
        },

    },
    components: {
        HeadTop
    }
}

</script>

<style lang="css">
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: 480px;
}


</style>
