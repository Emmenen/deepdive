<template>
    <el-input
      @change="inputchange"
      v-model="dataForm.amount"
      placeholder="Please input amount"
      show-word-limit></el-input>
</template>

<script>
export default {
    data(){
        return {

        }
    },
    props: {
        expString: ""
    },

}
</script>

<style>
</style>
