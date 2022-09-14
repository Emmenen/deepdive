<template>
    <div class="record">
        <head-top title="数据管理/交易记录"></head-top>
        <el-table :data="tableData"
          :default-sort="{ prop: 'modifiedDate', order: 'descending' }"
          style="width: 100%">
            <el-table-column
            prop="serialNumberA"
            label="交易甲方地址"  >
                <template #default="scope">
                    <el-tooltip
                    class="item"
                    effect="dark"
                    :content="scope.row.serialNumberA"
                    placement="top-start">
                        <el-tag>{{ scope.row.serialNumberA }}</el-tag>
                    </el-tooltip>
                </template>
            </el-table-column>
            <el-table-column prop="serialNumberB" label="交易乙方地址" >
                <template #default="scope">
                    <el-tooltip
                    class="item"
                    effect="dark"
                    :content="scope.row.serialNumberB"
                    placement="top-start">
                        <el-tag>{{ scope.row.serialNumberB }}</el-tag>
                    </el-tooltip>
                </template>
            </el-table-column>
            <el-table-column prop="amount" label="交易金额" >
            </el-table-column>
            <el-table-column
            sortable
            label="交易时间"
            >
                <template #default="scope">
                     <i class="el-icon-time"></i>
                     <span style="margin-left: 10px">{{ scope.row.modifiedDate }}</span>
                </template>
            </el-table-column>
        </el-table>
        <el-button v-loading="loading" @click="getData()">刷新</el-button>
    </div>
</template>

<script>
import headTop from "../components/headTop";
export default {
    data(){
        return {
            tableData:[],
            loading: false
        }
    },
    methods: {
        filterTag(value, row){
          return row.serialNumberA !== "";
        },
        getData(){
            this.loading = true

             this.$http({
                url: this.$http.adornUrl("/getHistory"),
                method: "post",
                data: this.$http.adornData()
            }).then((res)=>{
                this.tableData =
                res.data.filter((item)=>{
                   return item.serialNumberA != ""
                })
                this.loading = false
            }).catch(()=>{
                this.loading = false
            })
        }
    },
    created(){
        this.getData()
    },
    components: {
        headTop
    }
}
</script>

<style>
</style>
