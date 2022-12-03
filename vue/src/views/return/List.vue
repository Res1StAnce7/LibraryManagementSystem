<template>
  <div>
    <!--    Search List-->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="Book Name" v-model="params.name"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="Book Code" v-model="params.bookNo"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="Username" v-model="params.userName"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i> Search</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i> Reset</el-button>
    </div>

    <el-table :data="tableData" stripe row-key="id"  default-expand-all>
      <el-table-column prop="id" label="ID" width="50"></el-table-column>
      <el-table-column prop="bookName" label="Book Name" width="100"></el-table-column>
      <el-table-column prop="bookNo" label="Book Code" width="100"></el-table-column>
      <el-table-column prop="userNo" label="UserID"></el-table-column>
      <el-table-column prop="userName" label="Name"></el-table-column>
      <el-table-column prop="userPhone" label="Phone"></el-table-column>
      <el-table-column prop="score" label="Point"></el-table-column>
      <el-table-column prop="createtime" label="Date of Borrowing" width="130"></el-table-column>
      <el-table-column prop="status" label="Borrowing Status" width="130"></el-table-column>
      <el-table-column prop="days" label="Days of Borrowing" width="130"></el-table-column>
      <el-table-column prop="returnDate" label="Date of Return" width="120"></el-table-column>
      <el-table-column prop="realDate" label="Actual Return Date" width="130"></el-table-column>
      <el-table-column label="Operation" width="100">
        <template v-slot="scope">
          <el-popconfirm
              style="margin-left: 5px"
              title="Are you sure to delete this record? "
              confirmButtonText="Confirm"
              cancelButtonText="Cancel"
          >
            <el-button type="danger" >Delete</el-button>
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

  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie'

export default {
  name: 'ReturnList',
  data() {
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      tableData: [],
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        bookNo: ''
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get('/borrow/pageReturn', {
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
        bookName: '',
        bookNo: '',
        userName: ''
      }
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum
      this.load()
    },
    del(id) {
      request.delete("/borrow/deleteReturn/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('Delete Success')
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    },
    returnBooks(row) {

    }
  }
}
</script>

<style scoped>

</style>