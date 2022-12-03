<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">Add Admin</div>
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="200px">
      <el-form-item label="Name" prop="username">
        <el-input v-model="form.username" placeholder="Name"></el-input>
      </el-form-item>
      <el-form-item label="Phone" prop="phone">
        <el-input v-model="form.phone" placeholder="Phone"></el-input>
      </el-form-item>
      <el-form-item label="Email" prop="email">
        <el-input v-model="form.email" placeholder="Email"></el-input>
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
  name: 'AddAdmin',
  data() {
    const checkPhone = (rule, value, callback) => {
      if (!/(1\s?)?(\d{3}|\(\d{3}\))[\s\-]?\d{3}[\s\-]?\d{4}$/.test(value)) {
        callback(new Error('Enter a valid phone number'));
      }
      callback()
    };
    return {
      form: {},
      rules: {
        username: [
          { required: true, message: 'Required', trigger: 'blur'},
          { min: 3, max: 20, message: 'Length should between 3 and 20', trigger: 'blur'}
        ],
        phone: [
          { required: true, message: 'Invalid Phone Number', validator: checkPhone, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/admin/save', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('Add successfully')
              this.$refs['ruleForm'].resetFields()
            } else {
              this.$notify.error(res.msg)
            }
          })
        }
      })
    }
  }
}

</script>

