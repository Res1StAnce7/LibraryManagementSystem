<template>
  <div>
    <!--    Search List-->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="Input book name" v-model="params.name"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="Input book code" v-model="params.bookNo"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="Input username" v-model="params.userName"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i> Search</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i> Rest</el-button>
    </div>

    <el-table :data="tableData" stripe row-key="id"  default-expand-all>
      <el-table-column prop="id" label="No." width="80"></el-table-column>
      <el-table-column prop="bookName" label="Book Name"></el-table-column>
      <el-table-column prop="bookNo" label="Book Code"></el-table-column>
      <el-table-column prop="userNo" label="UserID"></el-table-column>
      <el-table-column prop="userName" label="User Name"></el-table-column>
      <el-table-column prop="userPhone" label="User Contact Information"></el-table-column>
      <el-table-column prop="score" label="Score"></el-table-column>
      <el-table-column prop="createTime" label="Date of Borrowing"></el-table-column>
      <el-table-column prop="status" label="Borrowing Status"></el-table-column>
      <el-table-column prop="days" label="Days of Borrowing"></el-table-column>
      <el-table-column prop="returnDate" label="Date of Return"></el-table-column>
      <el-table-column prop="realDate" label="Actual Return Date"></el-table-column>
      <el-table-column label="Operation">
        <template v-slot="scope">
          <el-popconfirm
              style="margin-left: 5px"
              title="Are you sure to delete this record? "
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
      request.get('/borrow/pageRetur', {
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