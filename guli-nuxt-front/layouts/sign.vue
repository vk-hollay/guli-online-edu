<template>
  <div class="sign">
    <!--标题-->
    <div class="logo">
      <img src="~/assets/img/logo.png" alt="logo" @click="index()" >
    </div>
    
    <!--表单-->
    <nuxt/>
    
  </div>
</template>

<script>

export default {
  methods: {
    index() {
      // this.$router.push({path:'/'})
      window.location.href = "/";
    }
  }
}
</script>
