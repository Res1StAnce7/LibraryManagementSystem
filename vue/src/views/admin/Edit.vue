<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">Edit Admin</div>
    <el-form :inline="true" :model="form" label-width="200px">
      <el-form-item label="Username" prop="username">
        <el-input v-model="form.username" placeholder="Input the name"></el-input>
      </el-form-item>
      <el-form-item label="Contact Information" prop="phone">
        <el-input v-model="form.phone" placeholder="Input the contact information"></el-input>
      </el-form-item>
      <el-form-item label="Email">
        <el-input v-model="form.email" placeholder="Input the email"></el-input>
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
  name: 'EditAdmin',
  data() {
    return {
      form: {}
    }
  },
  created() {
    const id = this.$route.query.id
    request.get("/admin/" + id).then(res => {
      this.form = res.data
    })
  },
  methods: {
    save() {
      request.put('/admin/update', this.form).then(res => {
        if (res.code === '200') {
          this.$notify.success('Update successfully');
          this.$router.push("/adminList")
        } else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}

</script>

