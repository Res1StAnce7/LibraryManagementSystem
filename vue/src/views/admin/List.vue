<template>
  <div>
    <!--    Search List-->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="Name" v-model="params.username"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="Phone" v-model="params.phone"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="Email" v-model="params.email"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i> Search</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i> Reset</el-button>
    </div>

    <el-table :data="tableData" stripe empty-text=" ">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="username" label="Name"></el-table-column>
      <el-table-column prop="phone" label="Phone"></el-table-column>
      <el-table-column prop="email" label="Email" width="200"></el-table-column>
      <el-table-column prop="createtime" label="Time of Creation"></el-table-column>
      <el-table-column prop="updatetime" label="Time of Update"></el-table-column>
      <el-table-column label="Status" width="100">
        <template v-slot="scope">
          <el-switch
              v-model="scope.row.status"
              @change="changeStatus(scope.row)"
              active-color="#13ce66"
              inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="Operation" width="230">
        <template v-slot="scope">
          <el-button type="primary" @click="$router.push('/editAdmin?id=' + scope.row.id)">Edit</el-button>
          <el-popconfirm
              style="margin-left: 5px"
              title="Are you sure to delete this admin?"
              confirmButtonText="Confirm"
              cancelButtonText="Cancel"
              @confirm="del(scope.row.id)">
            <el-button type="danger" slot="reference">Delete</el-button>
          </el-popconfirm>
          <el-button style="margin-top: 10px" type="warning" @click="handleChangePass(scope.row)">Change Password</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--    Subpage-->
    <div style="margin-top: 20px">
      <el-pagination
          background
          :current-page="params.pageNum"
          :page-size="params.pageSize"
          layout="prev, pager, next"
          @current-change="handleCurrentChange"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="Change Password" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="100px" ref="formRef" :rules="rules">
        <el-form-item label="New Password" prop="newPass">
          <el-input v-model="form.newPass" autocomplete="off" show-password></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="savePass">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie'

export default {
  name: 'AdminList',
  data() {
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      tableData: [],
      total: 0,
      form: {},
      dialogFormVisible: false,
      params: {
        pageNum: 1,
        pageSize: 10,
        username: '',
        phone: '',
        email: ''
      },
      rules: {
        newPass: [
          {required: true, message: 'Required', trigger: 'blur'},
          {min: 3, max: 10, message: 'Length should between 3 and 10', trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    changeStatus(row) {
      if (this.admin.id === row.id && !row.status) {
        row.status = true
        this.$notify.warning('Illegal operation')
        return
      }
      request.put('/admin/update', row).then(res => {
        if (res.code === '200') {
          this.$notify.success('Successful operation')
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    },
    handleChangePass(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    savePass() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          request.put('/admin/password', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success("Successful operation")
              if (this.form.id === this.admin.id) {
                Cookies.remove('admin')
                this.$router.push('/login')
              } else {
                this.load()
                this.dialogFormVisible = false
              }
            } else {
              this.$notify.error("Failed to change password")
            }
          })
        }
      })
    },
    load() {
      request.get('/admin/page', {
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 10,
        username: '',
        phone: '',
        email: ''
      }
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum
      this.load()
    },
    del(id) {
      request.delete("/admin/delete/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('Successful operation')
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>

</style>