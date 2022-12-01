<template>
  <div>
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="Input your name" v-model="params.name"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="Input your contact information" v-model="params.phone"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i> Search</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i> Rest</el-button>
    </div>

    <el-table :data="tableData" stripe>
      <el-table-column prop="id" label="id" width="80"></el-table-column>
      <el-table-column prop="username" label="Username"></el-table-column>
      <el-table-column prop="name" label="Name"></el-table-column>
      <el-table-column prop="age" label="Age"></el-table-column>
      <el-table-column prop="address" label="Address"></el-table-column>
      <el-table-column prop="phone" label="Contact"></el-table-column>
      <el-table-column prop="Gender" label="Gender"></el-table-column>
      <el-table-column prop="account" label="Score"></el-table-column>
      <el-table-column label="Status">
        <template v-slot="scope">
          <el-switch
              v-model="scope.row.status"
              @change="changeStatus(scope.row)"
              active-color="#13ce66"
              inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="Time of Creation" width="120"></el-table-column>
      <el-table-column prop="updateTime" label="Time of Update" width="120"></el-table-column>

      <el-table-column label="Operation">
        <template v-slot="scope">
          <el-button type="warning" @click="handleAccountAdd(scope.row)">Recharge</el-button>
          <el-button type="primary" @click="$router.push('/editUser?id=' + scope.row.id)">Edit</el-button>
          <el-popconfirm
              style="margin-left: 5px"
              title="Are you sure to delete this user?"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">Delete</el-button>
          </el-popconfirm>
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

    <el-dialog title="Recharge" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="100px" ref="ruleForm" :rules="rules" style="width: 85%">
        <el-form-item label="Current Score" prop="account">
          <el-input disabled v-model="form.account" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Score" prop="score">
          <el-input v-model="form.score" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="addAccount">Confirm</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: 'User',
  data() {
    const checkNums = (rule, value, callback) => {
      value = parseInt(value)
      if (value < 10 || value > 200) {
        callback(new Error('Score must be between 10 and 200'));
      }
      callback()
    };
    return {
      tableData: [],
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        phone: ''
      },
      dialogFormVisible: false,
      form: {},
      rules: {
        score: [
          { required: true, message: 'Input the score', trigger: 'blur'},
          { validator: checkNums, trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    changeStatus(row) {
      request.put('/user/update', row).then(res => {
        if (res.code === '200') {
          this.$notify.success('Operation successful')
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    },
    load() {
      // fetch('http://localhost:9090/user/list').then(res => res.json()).then(res => {
      //   console.log(res)
      //   this.tableData = res
      // })

      request.get('/user/page', {
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
        name: '',
        phone: ''
      }
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum
      this.load()
    },
    del(id) {
      request.delete("/user/delete/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('Operation successful')
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    },
    handleAccountAdd(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    addAccount() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/user/account', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('Operation successful')
              this.dialogFormVisible = false
              this.load()
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

<style scoped>

</style>