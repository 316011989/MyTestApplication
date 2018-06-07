/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * 
 * React-Native中文官方站的demo文档,存在bug,rendermovie中的movie使用需要.item才可以取到值
 */

import React, {
  Component,
} from 'react';
//引入component和UI组件
import {
  AppRegistry,
  Image,
  FlatList,
  StyleSheet,
  Text,
  View,
  Navigator,
} from 'react-native';

//常量请求地址
var REQUEST_URL = 'https://raw.githubusercontent.com/facebook/react-native/0.51-stable/docs/MoviesExample.json';

//component组件
export default class SampleAppMovies extends Component {
  //构造函数
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      loaded: false,
    };
    // 在ES6中，如果在自定义的函数里使用了this关键字，则需要对其进行“绑定”操作，否则this的指向会变为空
    // 像下面这行代码一样，在constructor中使用bind是其中一种做法（还有一些其他做法，如使用箭头函数等）
    this.fetchData = this.fetchData.bind(this);
  }

  //overwrite的方法,相当于loadfinish
  componentDidMount() {
    this.fetchData();
  }

  fetchData() {
    //发送请求
    fetch(REQUEST_URL)
      .then((response) => response.json())
      .then((responseData) => {
        // 注意，这里使用了this关键字，为了保证this在调用时仍然指向当前组件，我们需要对其进行“绑定”操作
        this.setState({
          data: this.state.data.concat(responseData.movies),
          loaded: true,
        });
      });
  }

  //当state变化时会自动渲染
  render() {
    if (!this.state.loaded) {
      return this.renderLoadingView();
    }
    
    return (
      <FlatList
        data={this.state.data}
        renderItem={this.renderMovie}
        style={styles.list}
      />
    );
  }

  //渲染加载中效果
  renderLoadingView() {
    return (
      <View style={styles.container}>
        <Text>
          Loading movies...
        </Text>
      </View>
    );
  }

  /**
   * 渲染电影内容
   */
  renderMovie(movie) {
    return (
      <View style={styles.container}>
        <Image
          source={{ uri: movie.item.posters.thumbnail }}
          style={styles.thumbnail}
        />
        <View style={styles.rightContainer}>
          <Text style={styles.title}>{movie.item.title}</Text>
          <Text style={styles.year}>{movie.item.year}</Text>
        </View>
      </View>
    );
  }
}

//style
var styles = StyleSheet.create({
  container: {
    flex: 1,//权重
    flexDirection: 'row',//布局方向
    justifyContent: 'center',//内容相对容器位置
    alignItems: 'center',//
    backgroundColor: '#F5FCFF',//背景颜色
  },
  rightContainer: {
    flex: 1,
  },
  title: {
    fontSize: 20,//字体大小
    marginBottom: 8,//距离下边距
    textAlign: 'center',//文字位置
  },
  year: {
    textAlign: 'center',
  },
  thumbnail: {
    width: 53,//宽
    height: 81,//高
  },
  list: {
    paddingTop: 20,//内部距离顶部
    backgroundColor: '#F5FCFF',
  },
});

//主入口
AppRegistry.registerComponent('mytestapplication', () => SampleAppMovies);