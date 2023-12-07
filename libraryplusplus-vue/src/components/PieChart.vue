<template>
  <div>
    <Pie :options="chartOptions" :data="chartData"/>
  </div>
</template>

<script>
import { Pie } from "vue-chartjs";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";

ChartJS.register(ArcElement, Tooltip, Legend);

export default {
  name: "PieChart",
  components: { Pie },
  props: {
    dataList: Object,
  },
  computed: {
    chartData() {
      let data = this.dataList.values ? this.dataList.values.map(Number) : []
      let total = data.reduce((acc, value) => acc + value, 0);
      let percentages = data.map(value => ((value / total) * 100).toFixed(2) + '%');

      return {
        labels: this.dataList.labels,
        datasets: [
          {
            backgroundColor: this.generateColor(data.length),
            data: data,
            dataPercentage: percentages,
          },
        ],
      }
    },
    chartOptions() {
      return {
        responsive: true,
      }
    }
  },
  methods: {
    generateColor (count) {
      let colors = [];
      for (let i = 0; i < count; i++) {
        let color = `#${Math.floor(Math.random() * 16777215).toString(16)}`;
        colors.push(color)
      }
      return colors;
    }
  }

};
</script>

<style scoped>
/* ... */
</style>
