<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">Edit Category</div>
    <el-form :inline="true" :rules="rules" ref="ruleForm" :model="form" label-width="100px">
      <el-form-item label="Name" prop="name">
        <el-input v-model="form.name" placeholder="Category Name"></el-input>
      </el-form-item>
      <el-form-item label="Comment" prop="remark">
        <el-input v-model="form.remark" placeholder="Comment"></el-input>
      </el-form-item>
    </el-form>

    <div style="text-align: center; margin-top: 30px">
      <el-button type="primary" @click="save" size="medium">Submit</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: 'EditCategory',
  data() {
    return {
      form: {},
      rules: {
        name: [
          { required: true, message: 'Required', trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    const id = this.$route.query.id
    request.get("/category/" + id).then(res => {
      this.form = res.data
    })
  },
  methods: {
    save() {
      request.put('/category/update', this.form).then(res => {
        if (res.code === '200') {
          this.$notify.success('Update successfully')
          this.$router.push("/categoryList")
        } else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}

</script>

