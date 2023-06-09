<template>
  <div>
    <select v-model="sidoCode" @change="getGugunList">
      <option value="" disabled selected>--시도--</option>
      <option
        v-for="sido in sidoCodeList"
        :key="sido.sido_code"
        :value="sido.sido_code">
        {{ sido.sido_name }}
      </option>
    </select>
    &nbsp;
    <select v-model="gugunCode" @change="getCampList">
      <option value="" disabled selected>--구군--</option>
      <option
        v-for="gugun in gugunCodeList"
        :key="gugun.gugun_code"
        :value="gugun.gugun_code">
        {{ gugun.gugun_name }}
      </option>
    </select>
    <br />
    <br />
    <div id="map"></div>
  </div>
</template>

<script>
import http from "@/util/http.js";

export default {
  data() {
    return {
      map: null,
      sidoCodeList: [],
      sidoCode: 0,
      gugunCodeList: [],
      gugunCode: 0,
      campList: [],
      positions: [],
      markers: [],
    };
  },
  created() {
    this.getSidoList();
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.loadMap();
    } else {
      this.loadScript();
    }
  },
  methods: {
    loadScript() {
      const script = document.createElement("script");
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=a085fb7db79f6b2080753310bed6fbde&autoload=false";
      /*global kakao*/
      script.onload = () => window.kakao.maps.load(this.loadMap);

      document.head.appendChild(script);
    },
    loadMap() {
      const container = document.getElementById("map");
      const options = {
        center: new window.kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };

      this.map = new window.kakao.maps.Map(container, options);
    },
    getSidoList() {
      http.get("/camp/location").then((res) => {
        this.sidoCodeList = res.data;
      });
    },
    getGugunList() {
      this.gugunCode = 0;
      http.get(`/camp/location/${this.sidoCode}`).then((res) => {
        this.gugunCodeList = res.data;
      });
    },
    getCampList() {
      http
        .get(`/camp/location/${this.sidoCode}/${this.gugunCode}`)
        .then((res) => {
          this.campList = res.data;
          this.getPositions();
          if (this.positions.length > 0) {
            this.loadMaker();
          } else {
            alert("해당 위치에 캠핑장이 존재하지 않습니다");
          }
        });
    },
    getPositions() {
      this.positions = [];
      this.campList.forEach((camp) => {
        let obj = {};
        obj.title = camp.title;
        obj.latlng = new kakao.maps.LatLng(camp.latitude, camp.longitude);
        obj.content_id = camp.content_id;

        this.positions.push(obj);
      });
    },
    loadMaker() {
      this.deleteMarker();

      this.markers = [];
      this.positions.forEach((position) => {
        const marker = new kakao.maps.Marker({
          map: this.map,
          position: position.latlng,
          title: position.title,
        });
        kakao.maps.event.addListener(marker, "click", () => {
          console.log(position.content_id);
          this.$emit("goCampDetail", position.content_id);
        });
        this.markers.push(marker);
      });

      const bounds = this.positions.reduce(
        (bounds, position) => bounds.extend(position.latlng),
        new kakao.maps.LatLngBounds()
      );

      this.map.setBounds(bounds);
    },
    deleteMarker() {
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          item.setMap(null);
        });
      }
    },
  },
};
</script>

<style scoped>
#map {
  margin-left: auto;
  margin-right: auto;
  width: 50%;
  height: 500px;
}
select {
  width: 100px;
}
</style>
