<template>
  <div>
    <div style="margin: 20px 0">
      <el-select class="input" v-model="timeRange" placeholder="Please select" @change="load">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
    </div>
    <el-card>
      <div id="line" style="width: 100%; height: 400px"></div>
    </el-card>
  </div>
</template>

<script>
import Cookies from 'js-cookie'
import request from "@/utils/request";
import * as echarts from 'echarts'

const option = {
  title: {
    text: 'Book Borrowing and Returning Statistics'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: ['Number of Borrowing', 'Number of Returning']
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  toolbox: {
    feature: {
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: 'Number of Borrowing',
      type: 'line',
      stack: 'Total',
      smooth: true,
      data: []
    },
    {
      name: 'Number of Returning',
      type: 'line',
      stack: 'Total',
      smooth: true,
      data: []
    }
  ]
}

  export default {
    data() {
      return {
        admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
        lineBox: null,
        timeRange: 'week',
        options: [
          {label: 'Past Week', value: 'week'},
          {label: 'Past Month', value: 'month'},
          {label: 'Past Two Months', value: 'month2'},
          {label: 'Past Three Months', value: 'month3'},
        ]
      }
    },
    mounted() {
      this.load()
    },
    methods: {
      load() {
        if (!this.lineBox) {
          this.lineBox = echarts.init(document.getElementById('line'))
        }
        request.get('/borrow/lineCharts/' + this.timeRange).then(res => {
          option.xAxis.data = res.data.date
          option.series[0].data = res.data.borrow
          option.series[1].data = res.data.return
          this.lineBox.setOption(option)
        })
      }
    }
  }
</script>

<style>
.input {
  width: 300px;
}
</style>
