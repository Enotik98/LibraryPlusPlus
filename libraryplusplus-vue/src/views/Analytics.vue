<template>
  <div class="container d-flex justify-content-center mt-3">
    <div class="analytics">
      <label class="form-label">Period</label>
      <VueDatePicker v-model="date" range multi-calendars :enable-time-picker="false"/>
      <label class="form-label">Type Report</label>
      <select class="form-select" v-model="report">
        <option value="genre">Popularity of book genre</option>
        <option value="books">Popularity of books</option>
        <option value="restrictions">Restrictions Users</option>
        <option value="lostBooks">Lost Books</option>
      </select>
      <button @click="getReport" class="btn btn-outline-dark w-100">Get</button>

      <div v-if="excelData.body" class="card">
        <table class="table">
          <thead v-html="excelData.header"></thead>
          <tbody v-html="excelData.body"></tbody>
        </table>
        <div v-if="downloadLink">
          <a :href="downloadLink">Download</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as XLSX from "xlsx";
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'
import {sendRequest} from "@/scripts/request";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Analytics",
  components: { VueDatePicker},
  data() {
    return {
      date: [new Date(2023, 9, 13), new Date()],
      period: {
        // start: new Date(),
        start: new Date(),
        end: new Date()
      },
      report: "genre",
      excelData: {
        header: null,
        body: null,
      },
      downloadLink: null,
    }
  },
  methods: {
    async showAnalytics(response) {
      const excelBlob = await response.blob();
      const workbook = XLSX.read(await excelBlob.arrayBuffer(), {type: 'array'});

      const sheet = workbook.Sheets[workbook.SheetNames[0]];
      const data = XLSX.utils.sheet_to_json(sheet, {header: 1});
      const headers = data[0];

      data.shift();
      this.excelData.header = headers.map(header => `<th>${header}</th>`).join('');
      if (data.length !== 0) {
        this.excelData.body = data.map(row => `<tr>${row.map(cell => `<td>${cell}</td>`).join('')}</tr>`).join('');
      }

      const url = URL.createObjectURL(excelBlob);
      this.downloadLink = url;
    },
    async getReport() {
      try {
        let path = "";
        let method = ""
        switch (this.report) {
          case "genre": {
            path = "genre-excel";
            method = "POST";
            break;
          }
          case "books": {
            path = "book-excel";
            method = "POST";
            break;
          }
          case "restrictions": {
            path = "restrictions_excel";
            method = "GET";
            break;
          }
          case "lostBooks": {
            path = "lost_book_excel";
            method = "GET";
            break;
          }
          default :
            return;
        }
        this.period.start = this.date[0];
        this.period.end = this.date[1];
        const response = await sendRequest("/analytics/" + path, method, (method === "GET") ? null : this.period);

        if (!response.ok) {
          console.error("error ");
          this.excelData = null;
          return;
        }
        await this.showAnalytics(await response);

      } catch (e) {
        console.error(e)
      }
    }
  }
}
</script>

<style scoped>
.analytics {
  min-width: 40em;
}

.analytics > label {
  padding-top: 1em;

  color: var(--blue-opacity);
  font-size: 16pt;
  font-weight: 500;
}

.analytics > button {
  margin: 1.5em 0;
}
.card {
  padding: 1em;
}

</style>