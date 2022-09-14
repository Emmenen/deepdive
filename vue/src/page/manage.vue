<template>
	<div class="manage_page fillcontain">
        <el-row style="height: 100%; " >
            <el-col :span="4"  style="min-height: 100%; background-color: #545c64;">
                <el-menu :default-active="defaultActive" background-color="#545c64"
                         text-color="#fff"
                         active-text-color="#ffd04b" style="min-height: 100%;" collapse-transition  theme="dark" router>
                    <el-menu-item index="manage"><i class="el-icon-menu"></i>首页</el-menu-item>
                    <el-submenu index="2">
                        <template slot="title"><i class="el-icon-document"></i>铸造厂</template>
                        <el-menu-item index="coinList">发行的币</el-menu-item>
<!--                        <el-menu-item index="nftList">发行的NFT</el-menu-item>-->
                        <el-menu-item index="mint">铸造</el-menu-item>
                    </el-submenu>
                    <el-submenu index="3">
                        <template slot="title"><i class="el-icon-plus"></i>交易</template>
                        <el-menu-item index="transferRecord">交易记录</el-menu-item>
                        <el-menu-item index="transfer">发起交易</el-menu-item>
                        <el-menu-item index="burn">释放</el-menu-item>
                    </el-submenu>
<!--                    <el-submenu index="4">-->
<!--                        <template slot="title"><i class="el-icon-star-on"></i>图表</template>-->
<!--                        <el-menu-item index="visitor">用户分布</el-menu-item>-->
<!--                        &lt;!&ndash; <el-menu-item index="newMember">用户数据</el-menu-item> &ndash;&gt;-->
<!--                    </el-submenu>-->
<!--                    <el-submenu index="5">-->
<!--                        <template slot="title"><i class="el-icon-edit"></i>编辑</template>-->
<!--                        &lt;!&ndash; <el-menu-item index="uploadImg">上传图片</el-menu-item> &ndash;&gt;-->
<!--                        <el-menu-item index="vueEdit">文本编辑</el-menu-item>-->
<!--                    </el-submenu>-->
<!--                    <el-submenu index="6">-->
<!--                        <template slot="title"><i class="el-icon-setting"></i>设置</template>-->
<!--                        <el-menu-item index="adminSet">管理员设置</el-menu-item>-->
<!--                        &lt;!&ndash; <el-menu-item index="sendMessage">发送通知</el-menu-item> &ndash;&gt;-->
<!--                    </el-submenu>-->
<!--                    <el-submenu index="7">-->
<!--                        <template slot="title"><i class="el-icon-warning"></i>说明</template>-->
<!--                        <el-menu-item index="explain">说明</el-menu-item>-->
<!--                    </el-submenu>-->
                </el-menu>
            </el-col>
            <el-col :span="20" style="height: 100%;overflow: auto; ">
                <keep-alive>
                    <router-view></router-view>
                </keep-alive>
            </el-col>
        </el-row>
  	</div>
</template>

<script>

    export default {
        data(){
            return {
                isCollapse: true,
            }
        },
		computed: {
			defaultActive: function(){
				return this.$route.path.replace('/', '');
			}
		},

    }
</script>


<style lang="less" scoped>
	@import '../style/mixin';
	.manage_page{

	}
    //.el-menu-item{
    //    color: #fff;
    //}
    //.el-menu{
    //    background-color: #324057;
    //}

</style>
