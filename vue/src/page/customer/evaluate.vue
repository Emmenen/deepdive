<template>
    <div>
        <head-top title="店铺评价" style="margin-bottom: 20px"></head-top>
        <el-row>
            {{this.$router.store.storeId}}
        </el-row>
        <el-row>
            <el-col :span="2" :offset="4">
                <span>{{evaluateParams.a.key}}</span>
            </el-col>
            <el-col :span="4" >
                <el-rate v-model="evaluateParams.a.value"></el-rate>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="2" :offset="4">
                <span>{{evaluateParams.b.key}}</span>
            </el-col>
            <el-col :span="4" >
                <el-rate v-model="evaluateParams.b.value"></el-rate>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="2" :offset="4">
                <span>{{evaluateParams.c.key}}</span>
            </el-col>
            <el-col :span="4" >
                <el-rate v-model="evaluateParams.c.value"></el-rate>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import headTop from "../../components/headTop";
export default {
    data() {
        return {
            store: this.$router.store,
            evaluateParams:{
                a: {
                    key:"商品质量",
                    value: null,
                },
                b: {
                    key: "发货速度",
                    value: null,
                },
                c: {
                    key: "服务态度",
                    value: null,
                },
            }
        }
    },
    props: {
        storeId:{
            type: String,
        }
    },
    components: {
        headTop,
    },
    methods:{
    }

}
</script>
<style lang="css">

</style>
