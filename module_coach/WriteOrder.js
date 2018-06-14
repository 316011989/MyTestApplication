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
} from 'react-native';

export default class WriteOrderActivity extends React.Component {
    constructor(props) {
        super(props);
        this.state = {

        }
    }

    render() {
        return (
            <View style={{ flex: 1, backgroundColor: '#F6F6F6', }} >
                {/* 票面信息 */}
                <View style={{ margin: 10, backgroundColor: 'white', padding: 15, borderWidth: 0.5, borderRadius: 3, borderColor: '#DDDDDD', }}>
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
                        <TouchableOpacity style={{ flex: 1, alignSelf: 'flex-end' }}>
                            <Text style={{ fontSize: 16, textAlign: 'right', color: '#ff600A', }}>342.0</Text>
                        </TouchableOpacity>
                    </View>
                </View>
                {/* 乘车人信息 */}
                <View style={{ flex: 1, }}>
                    {/* 乘车人姓名手机号 */}
                    <View style={{ flexDirection: 'row', backgroundColor: 'white', paddingLeft: 15, borderColor: '#DDDDDD', borderTopWidth: 0.5, borderBottomWidth: 0.5, paddingTop: 10, paddingBottom: 10 }}>
                        <View style={{ alignSelf: 'center', flex: 1 }}>
                            <Text style={{ fontSize: 16, textAlign: 'left', color: 'black' }}>乘车人</Text>
                        </View>
                        <View style={{ flex: 3 }}>
                            <Text style={{ fontSize: 14, textAlign: 'left', color: 'black' }}>张一龙</Text>
                            <Text style={{ fontSize: 14, textAlign: 'left', color: 'black' }}>18611124087</Text>
                        </View>
                    </View>
                    {/* 乘车人身份证号 */}
                    <View style={{ flexDirection: 'row', backgroundColor: 'white', paddingLeft: 15, borderColor: '#DDDDDD', borderBottomWidth: 0.5, paddingTop: 18, paddingBottom: 18 }}>
                        <View style={{ flex: 1 }}>
                            <Text style={{ fontSize: 16, textAlign: 'left', color: 'black' }}>身份证号</Text>
                        </View>
                        <View style={{ flex: 3 }}>
                            <Text style={{ fontSize: 16, textAlign: 'left', color: 'black' }}>410725198906291210</Text>
                        </View>
                    </View>
                    {/* 费用归属 */}
                    <View style={{ flexDirection: 'row', backgroundColor: 'white', paddingLeft: 15, borderColor: '#DDDDDD', borderTopWidth: 0.5, borderBottomWidth: 0.5, marginTop: 15, paddingTop: 18, paddingBottom: 18 }}>
                        <View style={{ flex: 1 }}>
                            <Text style={{ fontSize: 16, textAlign: 'left', color: 'black' }}>费用归属</Text>
                        </View>
                        <View style={{ flex: 3 }}>
                            <Text style={{ fontSize: 16, textAlign: 'left', color: 'black' }}>研发部</Text>
                        </View>
                    </View>
                    {/* 出差原因 */}
                    <View style={{ flexDirection: 'row', backgroundColor: 'white', paddingLeft: 15, borderColor: '#DDDDDD', borderBottomWidth: 0.5, paddingTop: 18, paddingBottom: 18 }}>
                        <View style={{ flex: 1 }}>
                            <Text style={{ fontSize: 16, textAlign: 'left', color: 'black' }}>出差原因</Text>
                        </View>
                        <View style={{ flex: 3 }}>
                            <Text style={{ fontSize: 16, textAlign: 'left', color: 'black' }}>拜访客户</Text>
                        </View>
                    </View>
                </View>
                <View style={{ alignSelf: 'flex-end', flexDirection: 'row', backgroundColor: 'white', paddingLeft: 15, paddingTop: 8, paddingBottom: 8 }}>
                    <View style={{ flex: 1, alignSelf: 'center' }}>
                        <Text style={{ fontSize: 16, textAlign: 'left', color: 'black' }}>订单总金额</Text>
                    </View>
                    <View style={{ flex: 1, alignSelf: 'center' }}>
                        <Text style={{ fontSize: 22, textAlign: 'left', color: '#FF600A' }}>342.0</Text>
                    </View>
                    <TouchableOpacity style={{ flex: 2, backgroundColor: '#FF600A', borderRadius: 90, marginLeft: 10, marginRight: 10, paddingTop: 12, paddingBottom: 12 }} onPress={this.detail.bind(this)}>
                        <Text style={{ fontSize: 16, textAlign: 'center', color: 'white', }}>提交订单</Text>
                    </TouchableOpacity>
                </View>
            </View>
        );
    };

    //跳转到详情
    detail() {
        this.props.navigation.navigate('Detail');
    }

    //标题栏参数
    static navigationOptions = ({ navigation }) => ({
        headerTitle: (<Text style={{ color: "#000", fontSize: 18, alignSelf: 'center', textAlign: 'center', flex: 1 }}> {'填写订单'}</Text>),
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

//styles
const styles = StyleSheet.create({

});