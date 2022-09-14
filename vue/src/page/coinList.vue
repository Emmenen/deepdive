<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="table_container">
            <el-table
                :data="tableData"
                highlight-current-row
                style="width: 100%"
                @cell-dblclick="tableClick">
                <el-table-column
                  type="index"
                  width="100">
                </el-table-column>
                <el-table-column
                  property="createdAt"
                  label="发行日期"
                  width="220">
                </el-table-column>
                <el-table-column
                  property="amount"
                  label="数量"
                  width="220">
                </el-table-column>
                <el-table-column
                  property="tokenId"
                  label="tokenId">
                </el-table-column>
                <el-table-column
                  property="owner"
                  label="所有者">
                </el-table-column>
                <el-table-column
                  property="updatedAt"
                  label="更新时间">
                </el-table-column>
            </el-table>
<!--            <el-pagination background layout="prev, pager, next" :total="1000" />-->
            <div class="Pagination" style="text-align: left;margin-top: 10px;">
                <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="currentPage"
                  :page-size="limit"
                  layout="total, prev, pager, next"
                  :total="count">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    import headTop from '../components/headTop'
    import {getUserList, getTokenDetail, getNFTList} from '@/api/getData'
    export default {
        data(){
            return {
                tableData: [],
                currentRow: null,
                offset: 0,
                limit: 10,
                count: 1,
                currentPage: 1,
            }
        },
    	components: {
    		headTop,
    	},
        created(){
            this.initData();
        },
        methods: {
            async initData(){
                try{
                    const res = await getNFTList(this.offset,this.limit);
                    const data = res.data.data;
                    if (res.status === 200) {
                        this.count = data.total;
                        console.log( this.count)
                        this.tableData = data.list;
                    }else{
                        throw new Error('获取数据失败');
                    }
                }catch(err){
                    console.log('获取数据失败', err);
                }
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.offset = (val - 1)*this.limit;
                this.initData()
            },
            async getUsers(){
                const Users = await getUserList({offset: this.offset, limit: this.limit});
                this.tableData = [];
                Users.forEach(item => {
                    const tableData = {};
                    tableData.username = item.username;
                    tableData.registe_time = item.registe_time;
                    tableData.city = item.city;
                    this.tableData.push(tableData);
                })
            },
            async tableClick(row,column,cell,event){
                // const res = await getTokenDetail(row.tokenId);
                // console.log(res)
                this.$router.push({
                        path: "/tokenDetail",
                        query: {
                            tokenId: row.tokenId,
                            amount: row.amount,
                        }
                    }
                )
            }
        },
    }
</script>

<style lang="less">
	@import '../style/mixin';
    .table_container{
        padding: 20px;
    }
</style>
