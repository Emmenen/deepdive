<template>
    <div>
        <head-top></head-top>
        <el-row>
            <el-col :span="5" offset="1" v-loading="infoLoading">
                <el-card :body-style="{ padding: '4px' }" shadow="always">
                    <img :src="credit.externalUrl" class="image">
                    <div style="padding: 14px;">
                        <div class="bottom clearfix">
                            <time class="time">{{ data.mintTime }}</time>
<!--                            <time class="time">{{ metaDataUri }}</time>-->
                            <el-button type="text" class="button" >
                                <a :href="metaDataUri" target="_blank" >
                                    跳转
                                </a>
                            </el-button>
                        </div>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="12" offset="2" class="block" v-loading="infoLoading">
                <el-card>
                    <el-descriptions title="详细信息" :column="1" >
                        <el-descriptions-item label="通证 ID">{{tokenId}}</el-descriptions-item>
                        <el-descriptions-item label="GSID">{{ credit.gsId }}</el-descriptions-item>
                        <el-descriptions-item label="CreditId">{{ credit.creditId }}</el-descriptions-item>
                        <el-descriptions-item label="序列号">{{ credit.serialNumber }}</el-descriptions-item>
<!--                        <el-descriptions-item label="通证标准">{{data.type}}</el-descriptions-item>-->
                        <el-descriptions-item label="剩余数量">{{amount}}</el-descriptions-item>
                        <el-descriptions-item label="创作者">{{data.creator}}</el-descriptions-item>
                        <el-descriptions-item label="NFT发行时间">{{ data.mintTime }}</el-descriptions-item>
                        <el-descriptions-item label="Credit发行时间">{{ credit.createdAt }}</el-descriptions-item>
                    </el-descriptions>
                </el-card>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24" class="block">
                <el-table
                    :border="true"
                    highlight-current-row
                    resizable
                    :data="tableData"
                    style="width: 100%">
                    <el-table-column
                        :show-overflow-tooltip="true"
                        prop="transactionHash"
                        label="交易哈希"
                        width="180"/>
                    <el-table-column
                        :show-overflow-tooltip="true"
                        prop="from"
                        label="发送方"/>
                    <el-table-column
                        :show-overflow-tooltip="true"
                        prop="to"
                        label="接收方"/>
                    <el-table-column
                        :show-overflow-tooltip="true"
                        prop="value"
                        label="数量"/>
                    <el-table-column
                        prop="syncTimestamp"
                        :show-overflow-tooltip="true"
                        label="块龄"
                        :formatter="formDate"/>
                    <el-table-column
                        :show-overflow-tooltip="true"
                        prop="epochNumber"
                        label="纪元"/>
                </el-table>


            </el-col>

        </el-row>
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
</template>

<script>
import {getTokenDetail,getCreditByCreditId, getTransferRecord,getTokenURI} from '@/api/getData'
import HeadTop from '../../components/headTop'
import {intervalTime} from '../../utils/DateUtils'
export default {
    name: "tokenDetail",
    data() {
        return {
            // transferRecord的数据
            infoLoading: false,
            metaDataUri: "",
            tableData: [],
            // token的数据
            data: {
            },
            // 从token的metadataURI中获取到的metaData数据
            metaData:{
            },
            // 通过metaData中记录的creditId从外部的服务（碳交易所）获得的credit数据
            credit:{},
            offset: 0,
            limit: 10,
            count: 1,
            currentPage: 1,
        }
    },
    components: {
        HeadTop
    },
    props: {
        tokenId: {
            type: String
        },
        amount: {
            type: String
        }
    },
    created(){
        this.initData();
    },
    methods: {
        formDate(row, column, cellValue, index){
            var time = new Date().getTime();
           return intervalTime(time-cellValue*1000);
        },

        //初始化数据
        async initData() {
            this.infoLoading = true
            this.tokenId = this.$route.query.tokenId
            this.amount = this.$route.query.amount
            // 1.获取token的详细数据
            const res = await getTokenDetail(this.tokenId);
            //  1.1 从res中获取tokenData赋值给data
            this.data = res.data.data
            // 2.获取token的交易记录（在Conflux上）
            //  2.1 初始化请求的数据
            const data = {
                limit: this.limit,
                skip: this.offset,
                tokenId: this.tokenId,
            }
            // 2.2发起请求
            const res2 = await getTransferRecord(data);
            // 2.3 返回值中取到交易记录的数据
            const record = res2.data.data
            this.count = record.total
            this.tableData = record.list;
            // 3. 获取metadata
            await this.getTokenMetaData();
        },
        async getTokenMetaData(){
            // 通过tokenId获取tokenURI（即metadataURI）
            let res = await getTokenURI(this.tokenId);
            console.log("res",res)
            const tokenURI = res.data;
            this.metaDataUri = this.$http.nullUrl(tokenURI);
            console.log("tokenURI",tokenURI)
            // 请求tokenURI，获取tokenURI内部的json数据（即metaData）
            this.$http({
                url: this.$http.nullUrl(tokenURI),
                method: "GET"
            }).then(async (res) => {
                console.log(res)
                // 将数据赋值给metaData
                this.metaData = res.data;
                // 通过creditId获取Credit
                const res2 = await getCreditByCreditId(this.metaData.creditId);
                this.credit = res2.data;
                console.log("this.credit",this.credit)
                this.infoLoading = false
            })
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.offset = (val - 1)*this.limit;
            this.initData()
        },
    }
}
</script>

<style lang="css">

    .block{
        display: block;
        background: gray;
    }
    .time {
        font-size: 13px;
        color: #999;
    }

    .bottom {
        margin-top: 13px;
        line-height: 12px;
    }

    .button {
        padding: 0;
        float: right;
    }

    .image {
        width: 100%;
        display: block;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }

    .clearfix:after {
        clear: both
    }
</style>
