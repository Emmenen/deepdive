<template>
    <div>
        <head-top></head-top>
        <el-row>
            <el-col :span="18" offset="1">
                <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
                    <el-form-item label="发送方" prop="from">
                        <el-input v-model="formData.from" placeholder="请输入发送方" :maxlength="50" clearable :style="{width: '100%'}">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="接收方" prop="to">
                        <el-input v-model="formData.to" placeholder="请输入接收方" :maxlength="50" clearable :style="{width: '100%'}">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="tokenId" prop="tokenId">
                        <el-input v-model="formData.tokenId" placeholder="请输入tokenId" clearable :style="{width: '100%'}">
                        </el-input>
                    </el-form-item>
                    <el-form-item size="large">
                        <el-button type="primary" @click="submitForm">提交</el-button>
                        <el-button @click="resetForm">重置</el-button>
                    </el-form-item>
                </el-form>

            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24" class="block">
                <el-table
                    v-loading="tableLoading"
                    :border="true"
                    highlight-current-row
                    :header-cell-style="tableHeaderColor"
                    resizable
                    :data="tableData"
                    @header-click="headerClick"
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
                        :show-overflow-tooltip="true"
                        prop="tokenId"
                        label="tokenId"/>
                    <el-table-column
                        prop="syncTimestamp"
                        :show-overflow-tooltip="true"
                        class-name="clickAble"
                        :label="syncTimestampDisplay"
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
                :page-size="formData.limit"
                layout="total, prev, pager, next"
                :total="count">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import headTop from '@/components/headTop'
    import {getTransferRecord,getAllTransferRecord} from '@/api/getData'
    import {formDate,intervalTime} from '../utils/DateUtils'

    export default {
    	data(){
    		return {
                tableLoading: false,
                syncTimestampDisplay: "块领",
                tableData: [],
                formData:{
                    skip: 0,
                    limit: 10,
                    from: undefined,
                    to: undefined,
                    tokenId: undefined,
                },
                limit: 10,
                count: 1,
                currentPage: 1,
                rules: {
                    from: [],
                    to: [],
                    tokenId: [{
                        pattern: /^\d+$/,
                        message: '请输入数字',
                        trigger: 'blur'
                    }],
                },
            }
    	},
    	components: {
    		headTop,
    	},
    	mounted(){
    		this.initData();
    	},
    	methods: {
            formDate(row, column, cellValue, index){
                if (this.syncTimestampDisplay === "时间"){
                    return formDate(new Date(cellValue*1000));
                }else {
                    var time = new Date().getTime();
                    return intervalTime(time-cellValue*1000);
                }
            },
    		async initData(){
                this.tableLoading = true
                const res2 = await getTransferRecord(this.formData);
                const record = res2.data.data
                this.count = record.total
                this.tableData = record.list;
                this.tableLoading = false
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {

                this.currentPage = val;
                this.formData.skip = (val - 1)*this.limit;
                this.initData()
            },
            headerClick(column, event){
                if (column.property === "syncTimestamp"){
                    console.log(2)
                    if (this.syncTimestampDisplay === "时间"){
                        this.syncTimestampDisplay = "块领"
                    }else {
                        this.syncTimestampDisplay = "时间"
                    }
                }
            },
            //设置表头的颜色
            tableHeaderColor({ row, column, rowIndex, columnIndex }) {
                console.log(row, column, rowIndex, columnIndex);
                if (rowIndex === 0 && columnIndex === 5) {
                    return 'color:#20a0ff;';//橙色
                }
            },
            async submitForm() {
                this.$refs['elForm'].validate(valid => {
                    if (!valid) return
                })
                this.initData();
            },
            resetForm() {
                this.$refs['elForm'].resetFields()
            },
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
