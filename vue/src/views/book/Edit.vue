<template>
  <div style="width: 80%">
    <div style="margin-bottom: 30px">编辑图书</div>
    <el-form :inline="true" :rules="rules" ref="ruleForm" :model="form" label-width="100px">
      <el-form-item label="name" prop="name">
        <el-input v-model="form.name" placeholder="Input the name"></el-input>
      </el-form-item>
      <el-form-item label="Description" prop="description">
        <el-input style="width: 400px" type="textarea" v-model="form.description" placeholder="Input the description"></el-input>
      </el-form-item>
      <el-form-item label="Date of Publish" prop="publishDate">
        <el-date-picker
            v-model="form.publishDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="Select date of publish">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="Author" prop="author">
        <el-input v-model="form.author" placeholder="Input the author"></el-input>
      </el-form-item>
      <el-form-item label="Publisher" prop="publisher">
        <el-input v-model="form.publisher" placeholder="Input the publisher"></el-input>
      </el-form-item>
      <el-form-item label="Category" prop="category">
        <el-cascader
            style="width: 220px"
            :props="{ value: 'name', label: 'name'}"
            v-model="form.categories"
            :options="categories"></el-cascader>
      </el-form-item>
      <el-form-item label="Book Code" prop="bookNo">
        <el-input v-model="form.bookNo" placeholder="Input Book Code"></el-input>
      </el-form-item>
      <el-form-item label="Score" prop="cover">
        <el-input-number v-model="form.score" :min="10" :max="30" label="Score Needed"></el-input-number>
      </el-form-item>
      <br>
      <el-form-item label="Cover" prop="cover">
        <el-upload
            class="avatar-uploader"
            :action="'http://localhost:9090/api/book/file/upload?token=' + this.admin.token"
            :show-file-list="false"
            :on-success="handleCoverSuccess"
        >
          <img v-if="form.cover" :src="form.cover" class="avatar" alt="">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
    </el-form>

    <div style="text-align: center; margin-top: 30px">
      <el-button type="primary" @click="save" size="medium">Submit</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: 'EditBook',
  data() {
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      form: { score: 10 },
      categories: [],
      rules: {
        name: [
          { required: true, message: '请输入图书名称', trigger: 'blur'}
        ],
        bookNo: [
          { required: true, message: '请输入图书标准码', trigger: 'blur'}
        ],
        score: [
          { required: true, message: '请输入借书积分', trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    request.get('/category/tree').then(res => {
      this.categories = res.data
    })

    const id = this.$route.query.id
    request.get("/book/" + id).then(res => {
      this.form = res.data
      if (this.form.category) {
        this.form.categories = this.form.category.split(' > ')
        console.log(this.form.categories)
      }
    })
  },
  methods: {
    handleCoverSuccess(res) {
      if (res.code === '200') {
        this.form.cover = res.data
      }
    },
    save() {
      request.put('/book/update', this.form).then(res => {
        if (res.code === '200') {
          this.$notify.success('更新成功')
          this.$router.push("/bookList")
        } else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}

</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

