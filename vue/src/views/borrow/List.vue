<template>
  <div>
    <!--    Search List-->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="Book Name" v-model="params.bookName"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="Book Code" v-model="params.bookNo"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="User Name" v-model="params.userName"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i> Search</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i> Reset</el-button>
    </div>

    <el-table :data="tableData" stripe row-key="id" empty-text=" " default-expand-all>
      <el-table-column prop="id" label="ID"></el-table-column>
      <el-table-column prop="bookName" label="BookName" width="100"></el-table-column>
      <el-table-column prop="bookNo" label="Code"></el-table-column>
      <el-table-column prop="userNo" label="UserID"></el-table-column>
      <el-table-column prop="userName" label="Name"></el-table-column>
      <el-table-column prop="userPhone" label="Phone"></el-table-column>
      <el-table-column prop="score" label="PointsNeeded" width="100"></el-table-column>
      <el-table-column prop="createtime" label="TimeLent" width="100"></el-table-column>
      <el-table-column prop="status" label="Status"></el-table-column>
      <el-table-column prop="days" label="DaysLent"></el-table-column>
      <el-table-column prop="returnDate" label="DateReturning" width="110"></el-table-column>
      <el-table-column prop="note" label="Expiration" width="80">
        <template v-slot="scope">
          <el-tag type="success" v-if="scope.row.note === 'Normal'">{{ scope.row.note }}</el-tag>
          <el-tag type="primary" v-if="scope.row.note === 'About to Expire'">{{ scope.row.note }}</el-tag>
          <el-tag type="warning" v-if="scope.row.note === 'Expired'">{{ scope.row.note }}</el-tag>
          <el-tag type="danger" v-if="scope.row.note === 'Expired'">{{ scope.row.note }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Management" width="100">
        <template v-slot="scope">
          <el-button type="primary" @click="returnBooks(scope.row)" v-if="scope.row.status !== 'Returned'">Return</el-button>
        </template>
      </el-table-column>
      <el-table-column label="Operation" width="100">
        <template v-slot="scope">
          <el-popconfirm
              title="Are you sure to delete this record?"
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
  name: 'BorrowList',
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
      request.get('/borrow/page', {
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
      request.delete("/borrow/delete/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('Delete successfully')
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    },
    returnBooks(row) {
      request.post("/borrow/saveReturn", row).then(res => {
        if (res.code === '200') {
          this.$notify.success('Return successfully')
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