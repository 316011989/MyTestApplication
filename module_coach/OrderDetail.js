import React, {
    Component,
} from 'react';
//引入component和UI组件
import {
    Text,
    View,
    StyleSheet,
    Image,
    TouchableOpacity,
    ScrollView,
    YellowBox
} from 'react-native';

YellowBox.ignoreWarnings(['Warning: isMounted(...) is deprecated', 'Module RCTImageLoader']);

export default class OrderDetailActivity extends React.Component {
    constructor(props) {
        super(props);
        this.state = {

        }

    }

    render() {
        return (
            <ScrollView contentContainerStyle={{ backgroundColor: '#F6F6F6' }} >
                <Text style={{ fontSize: 18, textAlign: 'center' }}>已取消</Text>
                {/* 票面信息 */}
                <View style={[styles.infosArea1, { marginBottom: 10 }]}>
                    <View style={{ flexDirection: 'row', backgroundColor: 'white', marginBottom: 8, }}>
                        <Text style={{ fontSize: 14, textAlign: 'center', color: 'black' }}>06月11日 星期一</Text>
                        <Text style={{ fontSize: 14, textAlign: 'center', color: 'black', marginLeft: 10 }}>15:50</Text>
                    </View>
                    <View style={{ flexDirection: 'row', justifyContent: 'space-between', backgroundColor: 'white', marginBottom: 8 }}>
                        <Text style={{ fontSize: 22, textAlign: 'center' }}>北京市</Text>
                        <Text style={{ fontSize: 22, textAlign: 'center' }}>上海</Text>
                    </View>
                    <View style={{ flexDirection: 'row', justifyContent: 'space-between', backgroundColor: 'white' }}>
                        <Text style={{ fontSize: 15, textAlign: 'center' }}>赵公口汽车站</Text>
                        <Text style={{ fontSize: 15, textAlign: 'center' }}>上海</Text>
                    </View>
                    <View style={{ height: 1, backgroundColor: '#DDDDDD', marginTop: 15, marginBottom: 15 }}></View>
                    <View style={{ flexDirection: 'row', backgroundColor: 'white' }}>
                        <Text style={{ fontSize: 15, textAlign: 'center' }}>退改须知</Text>
                        <Text style={{ fontSize: 15, textAlign: 'left', marginLeft: 10 }}>暂不支持在线退改签</Text>
                    </View>
                </View>
                {/* 订单信息 */}
                <View style={styles.infosArea1}>
                    <View style={styles.infoItem1}>
                        <Text style={styles.textLeft}>订单编号</Text>
                        <Text style={styles.textRight}>YJ147788516691752378</Text>
                    </View>
                    <View style={styles.infoItem2}>
                        <Text style={styles.textLeft}>服务类型</Text>
                        <Text style={styles.textRight}>汽车票</Text>
                    </View>
                    <View style={styles.infoItem2}>
                        <Text style={styles.textLeft}>下单时间</Text>
                        <Text style={styles.textRight}>2018-10-31 11:39:54</Text>
                    </View>
                </View>
                {/* 乘车人信息 */}
                <View style={styles.infosArea2}>
                    {/* 姓名 */}
                    <View style={styles.infoItem1}>
                        <Text style={styles.textLeft}>姓名</Text>
                        <Text style={styles.textRight}>张一龙</Text>
                    </View>
                    {/* 乘车人身份证号 */}
                    <View style={styles.infoItem2}>
                        <Text style={styles.textLeft}>身份证号</Text>
                        <Text style={styles.textRight}>410725198906291210</Text>
                    </View>
                    {/* 手机号码 */}
                    <View style={styles.infoItem2}>
                        <Text style={styles.textLeft}>手机号</Text>
                        <Text style={styles.textRight}>18611124087</Text>
                    </View>
                    {/* 费用归属 */}
                    <View style={styles.infoItem2}>
                        <Text style={styles.textLeft}>费用归属</Text>
                        <Text style={styles.textRight}>研发部</Text>
                    </View>
                    {/* 出差原因 */}
                    <View style={styles.infoItem2}>
                        <Text style={styles.textLeft}>出差原因</Text>
                        <Text style={styles.textRight}>拜访客户</Text>
                    </View>
                </View>
                {/* 价格信息 */}
                <View style={styles.infosArea2}>
                    {/* 票价 */}
                    <View style={styles.infoItem1}>
                        <Text style={styles.textLeft}>票价</Text>
                        <Text style={styles.textRight}>342.00</Text>
                    </View>
                    {/* 保险 */}
                    <View style={styles.infoItem2}>
                        <Text style={styles.textLeft}>保险</Text>
                        <Text style={styles.textRight}>1.00</Text>
                    </View>
                    {/* 行云预订服务费 */}
                    <View style={styles.infoItem2}>
                        <Text style={styles.textLeft}>行云预订服务费</Text>
                        <Text style={styles.textRight}>5.00</Text>
                    </View>
                    {/* 优惠券 */}
                    <View style={styles.infoItem2}>
                        <Text style={styles.textLeft}>优惠券</Text>
                        <Text style={styles.textRight}>0.00</Text>
                    </View>
                    {/* 订单总额 */}
                    <View style={styles.infoItem2}>
                        <Text style={styles.textLeft}>订单总额</Text>
                        <Text style={styles.textRight}>347.00</Text>
                    </View>
                </View>
            </ScrollView >
        );
    }

    static navigationOptions = ({ navigation }) => ({
        headerTitle: (<Text style={{ color: "#000", fontSize: 18, alignSelf: 'center', textAlign: 'center', flex: 1 }}>订单详情</Text>),
        //右边按钮
        headerRight: (
            <View >
                <TouchableOpacity onPress={() => alert("hello")}>
                    <Text style={{ fontSize: 16, color: '#FF600A', padding: 10 }} >联系客服</Text>
                </TouchableOpacity>
            </View>
        ),
        //右边按钮
        headerLeft: (
            <TouchableOpacity onPress={() => navigation.goBack()}>
                <Image source={require('./icon/icon_back.png')} style={{ width: 20, height: 20, marginLeft: 8, marginRight: 8 }}></Image>
            </TouchableOpacity>
        ),
        //导航栏的title的style
        headerTitleStyle: {
            color: "#000", fontSize: 16, alignSelf: 'center', textAlign: 'center', flex: 1
        },
        //是否允许右滑返回，在iOS上默认为true，在Android上默认为false
        gesturesEnabled: true,
    });
}

const styles = StyleSheet.create({
    //订单信息块
    infosArea1: { flex: 1, padding: 15, backgroundColor: 'white', borderColor: '#DDDDDD', borderBottomWidth: 0.5, borderTopWidth: 0.5 },
    //乘车人信息块和价格信息块
    infosArea2: { flex: 1, padding: 15, backgroundColor: 'white', borderColor: '#DDDDDD', borderBottomWidth: 0.5 },
    //详情item的首行
    infoItem1: {
        flexDirection: 'row'
    },
    //详情item的非首行
    infoItem2: {
        flexDirection: 'row', marginTop: 5
    },
    //订单详情文字-左
    textLeft: {
        fontSize: 16, textAlign: 'left', color: 'black', flex: 1
    },
    //订单详情文字-右
    textRight: {
        fontSize: 16, textAlign: 'right', color: 'black', flex: 2
    }
})
