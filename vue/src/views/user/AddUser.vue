<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">Add User</div>
    <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="100px">
      <el-form-item label="Name" prop="name">
        <el-input v-model="form.name" placeholder="Name"></el-input>
      </el-form-item>
      <el-form-item label="Age" prop="age">
        <el-input v-model="form.age" placeholder="Age"></el-input>
      </el-form-item>
      <el-form-item label="Gender">
        <el-radio v-model="form.gender"  label="Male">Male</el-radio>
        <el-radio v-model="form.gender"  label="Female">Female</el-radio>
      </el-form-item>
      <el-form-item label="Phone" prop="phone">
        <el-input v-model="form.phone" placeholder="Phone"></el-input>
      </el-form-item>
      <el-form-item label="Address" prop="address">
        <el-input v-model="form.address" placeholder="Address"></el-input>
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
  name: 'AddUser',
  data() {
    const checkAge = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('Age cannot be empty'));
      }
      if (!/^[0-9]+$/.test(value)) {
        callback(new Error('Age must be a number'));
      }
      if (parseInt(value) > 120 || parseInt(value) <= 0) {
        callback(new Error('Age must be between 0 and 120'));
      }
      callback()
    };
    const checkPhone = (rule, value, callback) => {
      if (!/(1\s?)?(\d{3}|\(\d{3}\))[\s\-]?\d{3}[\s\-]?\d{4}$/.test(value)) {
        callback(new Error('Enter a valid phone number'));
      }
      callback()
    };
    return {
      form: {gender: 'Male'},
      rules: {
        name: [
          { required: true, message: 'Required', trigger: 'blur'}
        ],
        age: [
          { required: true, message: 'Required', validator: checkAge, trigger: 'blur' }
        ],
        phone: [
          { required: true, message: 'Required', validator: checkPhone, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/user/save', this.form).then(res => {
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

