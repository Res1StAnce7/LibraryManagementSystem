<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">Book Edition</div>
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
      <el-form-item label="Score Needed" prop="score">
        <el-input v-model="form.score" disabled></el-input>
      </el-form-item>
      <el-form-item label="Boom Quantity" prop="nums">
        <el-input v-model="form.nums" disabled ></el-input>
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
      <el-form-item label="User Name" prop="userName">
        <el-input disabled v-model="form.userName"></el-input>
      </el-form-item>
      <el-form-item label="User Contact Information" prop="userPhone">
        <el-input disabled v-model="form.userPhone" ></el-input>
      </el-form-item>
      <el-form-item label="User Score" prop="account">
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
  name: 'EditBook',
  data() {
    return {
      form: {},
      books: [],
      users: [],
      rules: {
        bookNo: [
          { required: true, message: 'Input the book code', trigger: 'blur'}
        ],
        userNo: [
          { required: true, message: 'Input the user id', trigger: 'blur'}
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

    const id = this.$route.query.id
    request.get("/borrow/" + id).then(res => {
      this.form = res.data
    })
  },
  methods: {
    save() {
      request.put('/borrow/update', this.form).then(res => {
        if (res.code === '200') {
          this.$notify.success('Successful operation')
          this.$router.push("/bookList")
        } else {
          this.$notify.error(res.msg)
        }
      })
    },
    selBook() {
      const book = this.books.find(v => v.bookNo === this.form.bookNo)
      request.get('/book/' + book.id).then(res => {
        this.form.bookName = res.data.name
        this.form.score = res.data.score
        this.form.nums = res.data.nums
      })
    },
    selUser() {
      const user = this.users.find(v => v.username === this.form.userNo)
      request.get('/user/' + user.id).then(res => {
        this.form.userName = res.data.name
        this.form.userPhone = res.data.phone
        this.form.account = res.data.account
      })
    }
  }
}

</script>

