<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">Borrow Record</div>
    <el-form :inline="true" :rules="rules" ref="ruleForm" :model="form" label-width="100px">
      <el-form-item label="Book Code" prop="bookNo">
        <el-select v-model="form.bookNo" clearable filterable placeholder="Please select" @change="selBook">
          <el-option
              v-for="item in books"
              :key="item.id"
              :label="item.bookNo"
              :value="item.bookNo">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Book Name" prop="bookName">
        <el-input v-model="form.bookName" disabled></el-input>
      </el-form-item>
      <el-form-item label="Point Needed" prop="score">
        <el-input v-model="form.score" disabled></el-input>
      </el-form-item>
      <el-form-item label="Book Quantity" prop="nums">
        <el-input v-model="form.nums" disabled></el-input>
      </el-form-item>
      <br />
      <el-form-item label="User ID" prop="userNo">
        <el-select v-model="form.userNo" filterable placeholder="Please select" @change="selUser">
          <el-option
              v-for="item in users"
              :key="item.id"
              :label="item.username"
              :value="item.username">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Username" prop="userName">
        <el-input disabled v-model="form.userName"></el-input>
      </el-form-item>
      <el-form-item label="User Phone Number" prop="userPhone">
        <el-input disabled v-model="form.userPhone" ></el-input>
      </el-form-item>
      <el-form-item label="User Points" prop="account">
        <el-input disabled v-model="form.account" ></el-input>
      </el-form-item>
      <el-form-item label="Number of Days Book Lent" prop="days">
        <el-input-number v-model="form.days" :min="1" :max="30" label="Number of Days Book Lent"></el-input-number>
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
  name: 'AddBook',
  data() {
    return {
      form: {days: 1},
      books: [],
      users: [],
      rules: {
        bookNo: [
          { required: true, message: 'Required', trigger: 'blur'}
        ],
        userNo: [
          { required: true, message: 'Required', trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    request.get('/book/list').then(res => {
      this.books = res.data
    })

    request.get('/user/list').then(res => {
      this.users = res.data.filter(v => v.status)
    })
  },
  methods: {
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/borrow/save', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('Borrow successfully')
              this.$refs['ruleForm'].resetFields()
            } else {
              this.$notify.error(res.msg)
            }
          })
        }
      })
    },
    selBook() {
      const book = this.books.find(v => v.bookNo === this.form.bookNo)
      request.get('/book/' + book.id).then(res => {
        this.$set(this.form, 'bookName', res.data.name)
        this.form.score = res.data.score
        this.form.nums = res.data.nums
      })
    },
    selUser() {
      const user = this.users.find(v => v.username === this.form.userNo)
      request.get('/user/' + user.id).then(res => {
        this.$set(this.form, 'userName', res.data.name)
        this.form.userPhone = res.data.phone
        this.form.account = res.data.account
      })
    }
  }
}

</script>

