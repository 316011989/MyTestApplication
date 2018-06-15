import React, {
  Component,
} from 'react';
//引入component和UI组件
import {
  Text,
  View,
  Image,
  StyleSheet,
  TouchableWithoutFeedback,
  TouchableOpacity,
  NativeModules,
} from 'react-native';

export default class MainActivity extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      departAddress: '北京',
      arrivalAddress: '上海'
    }
  }


  //交换出发地和目的地
  addressExchange() {
    var tempAddress = this.state.arrivalAddress;
    this.setState({
      arrivalAddress: this.state.departAddress,
      departAddress: tempAddress
    })
    console.log('output');
  }

  //查询车票
  checkTicket() {
    // this.props.navigation.navigate('Lines');
    var rnAndroidMethod = NativeModules.XingYun;
    rnAndroidMethod.getPackageName();
  }

  static navigationOptions = {
    // 展示数据 "`" 不是单引号 
    // title: `汽车票`,
    // 导航栏的标题, 可以是字符串也可以是个组件 会覆盖 title 的值 
    headerTitle: (<Text style={{ color: "#000", fontSize: 16, alignSelf: 'center', textAlign: 'center', flex: 1 }}> {'汽车票'}</Text>),
    //右边按钮
    headerRight: (
      <View >
        <Text style={{ padding: 10 }} onPress={() => alert("hello")}> </Text>
      </View>
    ),
    //右边按钮
    headerLeft: (
      <View >
        <Image source={require('./icon/icon_back.png')} style={{ width: 20, height: 20, marginLeft: 8, marginRight: 8 }}></Image>
      </View>
    ),
    // //左上角的返回键文字, 默认是上一个页面的title  IOS 有效
    // headerBackTitle: "返回",
    // //导航栏的style
    // headerStyle: {
    //   backgroundColor: '#fff',
    // },
    //导航栏的title的style
    headerTitleStyle: {
      color: "#000", fontSize: 16, alignSelf: 'center', textAlign: 'center', flex: 1
    },
    // //按压返回按钮显示的颜色 API > 5.0 有效
    // headerPressColorAndroid: 'blue',
    // //返回按钮的颜色
    // headerTintColor: 'red',
    //是否允许右滑返回，在iOS上默认为true，在Android上默认为false
    gesturesEnabled: true,
  };


  render() {
    return (
      <View style={styles.flex}>
        <View style={[styles.container, styles.borderStatus]}>
          <View style={styles.subItem}>
            <View style={styles.flex}>
              <Text style={styles.list_item_font}>出发地</Text>
            </View>
            <View style={[styles.flex]}>
              <Text style={{ fontSize: 22 }}>{this.state.departAddress}</Text>
            </View>
          </View>
          <View style={[styles.item, styles.center, styles.hCenter]}>
            <TouchableWithoutFeedback onPress={this.addressExchange.bind(this)}>
              <Image source={require('./icon/img_changelocation.png')} style={{ width: 40, height: 40 }} />
            </TouchableWithoutFeedback>
          </View>
          <View style={[styles.subItem, styles.rightAlign]}>
            <View style={[styles.flex]}>
              <Text style={styles.list_item_font}>目的地</Text>
            </View>
            <View style={[styles.flex]}>
              <Text style={{ fontSize: 22 }}>{this.state.arrivalAddress}</Text>
            </View>
          </View>
        </View>
        <View style={[styles.container]}>
          <View style={[styles.item, styles.center]}>
            <Text style={{ fontSize: 22 }}>6月6日</Text>
          </View>
          <View style={[styles.item, styles.center, styles.rightAlign]}>
            <Text style={{ fontSize: 22 }}>今天  ></Text>
          </View>
        </View>
        <View style={[styles.container]}>
          <TouchableOpacity style={[styles.item, styles.center, styles.buttonBgColor, styles.hCenter]}
            onPress={this.checkTicket.bind(this)}>
            <Text style={[styles.button_font, styles.fontColor]}>查 询</Text>
          </TouchableOpacity>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  flex: {
    flex: 1
  },

  topStatus: {
    marginTop: 64,
  },

  container: {
    marginLeft: 20,
    marginRight: 20,
    height: 128,
    flexDirection: 'row',
  },

  borderStatus: {
    borderBottomWidth: 2,
    borderBottomColor: '#ddd',
  },

  item: {
    height: 128,
    flex: 1,
  },

  subItem: {
    height: 64,
    marginTop: 32
  },

  list_item_font: {
    fontSize: 18
  },

  button_font: {
    fontSize: 24
  },

  center: {
    justifyContent: 'center',
  },

  hCenter: {
    alignItems: 'center'
  },

  rightAlign: {
    alignItems: 'flex-end'
  },

  buttonBgColor: {
    backgroundColor: '#FF600A',
    height: 60,
    borderRadius: 5
  },

  fontColor: {
    color: '#fff'
  }
})
