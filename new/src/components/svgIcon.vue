<!-- svg-sprite-loader svg雪碧图 转换工具 -->
<!-- <symbol> 元素中的 path 就是绘制图标的路径，这种一大串的东西我们肯定没办法手动的去处理，
那么就需要用到插件 svg-sprite-loader 帮助我们处理图标。此插件就是将 .svg 文件自动生成 <symbol>
 元素并插入进 <svg> 标签内，通过插件的处理就可以很方便解决生成<symbol> 图形模板对象的问题。-->
    <template>
        <svg class="svg-class" :class="className">
            <use :href="'#icon-' + iconName"></use>
        </svg>
     </template>
<script>
export default {
  name: 'SvgIcon',
  props: {
    // 图标名称 如 home
    iconName: {
      type: String,
      default: ''
    },
    // 对不同区域的 icon 样式调整，如字体大小
    className: {
      type: String,
      default: ''
    }
  },
  setup () {
    // 获取当前 svg 目录所有为 .svg 结尾的文件
    const req = require.context('@/assets/svg', true, /\.svg$/)

    // 解析获取的 .svg 文件的文件名称，并返回
    const requireAll = (requireContext) => {
      return requireContext.keys().map(requireContext)
    }
    requireAll(req)
  }
}
</script>
<style lang="less" scoped>
.svg-class{
    width: 1em;
    height: 1em;
    fill: currentColor;
    overflow: hidden
}
</style>
