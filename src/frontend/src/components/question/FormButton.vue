<template>
  <div>
    <v-card-actions>
      <v-spacer></v-spacer>
      <button class="button" @click="$router.push(`/questions`)">
        <span>돌아가기</span>
      </button>
      <slot></slot>
    </v-card-actions>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.button {
  background: #fff;
  border: none;
  padding: 2px;
  cursor: pointer;
  display: block;
  position: relative;
  overflow: hidden;
  transition: all 0.35s ease-in-out 0.35s;
  margin: 0 10px;
  text-align: center;
  border-radius: 4px;
}

span {
  display: block;
  padding: 7px 14px;
  background: #fff;
  z-index: 100;
  position: relative;
  transition: all 0.35s ease-in-out 0.35s;
}

.button:hover span {
  background: #36b4c7;
  color: #fff;
  transition: all 0.35s ease-in-out 0.35s;
}

.button:after {
  bottom: -100%;
  right: -100%;
  content: "";
  width: 100%;
  height: 100%;
  position: absolute;
  background: #36b4c7;
  transition: all 0.35s ease-in-out 0.5s;
}

.button:hover:after {
  right: 0;
  bottom: 0;
  transition: all ease-in-out 0.35s;
}

.button:before {
  top: -100%;
  left: -100%;
  content: "";
  width: 100%;
  height: 100%;
  position: absolute;
  background: #36b4c7;
  transition: all 0.35s ease-in-out 0.5s;
}

.button:hover:before {
  left: 0;
  top: 0;
  transition: all ease-in-out 0.35s;
}
</style>
