<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">Edit User</div>
    <el-form :inline="true" :model="form" label-width="100px">
      <el-form-item label="CardID">
        <el-input v-model="form.username" disabled></el-input>
      </el-form-item>
      <el-form-item label="Name">
        <el-input v-model="form.name" placeholder="Input the name"></el-input>
      </el-form-item>
      <el-form-item label="Age">
        <el-input v-model="form.age" placeholder="Input the age"></el-input>
      </el-form-item>
      <el-form-item label="Gender">
        <el-input v-model="form.gender" placeholder="Input the gender"></el-input>
      </el-form-item>
      <el-form-item label="Phone">
        <el-input v-model="form.phone" placeholder="Input the phone number"></el-input>
      </el-form-item>
      <el-form-item label="Address">
        <el-input v-model="form.address" placeholder="Input the address"></el-input>
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
  name: 'EditUser',
  data() {
    return {
      form: {}
    }
  },
  created() {
    const id = this.$route.query.id
    request.get("/user/" + id).then(res => {
      this.form = res.data
    })
  },
  methods: {
    save() {
      request.put('/user/update', this.form).then(res => {
        if (res.code === '200') {
          this.$notify.success('Update successfully');
          this.$router.push("/userList")
        } else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}

</script>

