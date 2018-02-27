package com.blankj.utilcode.util

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/02
 * desc  : 手机相关工具类
</pre> *
 */
object PhoneUtils {


    /**
     * 判断设备是否是手机
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    val isPhone: Boolean
        get() {
            val tm = Utils.app.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
            return tm != null && tm.phoneType != TelephonyManager.PHONE_TYPE_NONE
        }

    /**
     * 获取 IMEI 码
     *
     * 需添加权限
     * `<uses-permission android:name="android.permission.READ_PHONE_STATE" />`
     *
     * @return IMEI 码
     */
    val imei: String?
        @SuppressLint("HardwareIds", "MissingPermission")
        get() {
            val tm = Utils.app.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tm?.imei
            } else {
                tm?.deviceId
            }
        }

    /**
     * 获取 IMSI 码
     *
     * 需添加权限
     * `<uses-permission android:name="android.permission.READ_PHONE_STATE" />`
     *
     * @return IMSI 码
     */
    val imsi: String?
        @SuppressLint("HardwareIds", "MissingPermission")
        get() {
            val tm = Utils.app.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
            return tm?.subscriberId
        }

    /**
     * 获取移动终端类型
     *
     * @return 手机制式
     *
     *  * [TelephonyManager.PHONE_TYPE_NONE] : 0 手机制式未知
     *  * [TelephonyManager.PHONE_TYPE_GSM] : 1 手机制式为 GSM，移动和联通
     *  * [TelephonyManager.PHONE_TYPE_CDMA] : 2 手机制式为 CDMA，电信
     *  * [TelephonyManager.PHONE_TYPE_SIP] : 3
     *
     */
    val phoneType: Int
        get() {
            val tm = Utils.app.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
            return tm?.phoneType ?: -1
        }

    /**
     * 判断 sim 卡是否准备好
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    val isSimCardReady: Boolean
        get() {
            val tm = Utils.app.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
            return tm != null && tm.simState == TelephonyManager.SIM_STATE_READY
        }

    /**
     * 获取 Sim 卡运营商名称
     *
     * 中国移动、如中国联通、中国电信
     *
     * @return sim 卡运营商名称
     */
    val simOperatorName: String?
        get() {
            val tm = Utils.app.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
            return tm?.simOperatorName
        }

    /**
     * 获取 Sim 卡运营商名称
     *
     * 中国移动、如中国联通、中国电信
     *
     * @return 移动网络运营商名称
     */
    val simOperatorByMnc: String?
        get() {
            val tm = Utils.app.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
            val operator = tm?.simOperator ?: return null
            when (operator) {
                "46000", "46002", "46007" -> return "中国移动"
                "46001" -> return "中国联通"
                "46003" -> return "中国电信"
                else -> return operator
            }
        }

    /**
     * 获取手机状态信息
     *
     * 需添加权限
     * `<uses-permission android:name="android.permission.READ_PHONE_STATE" />`
     *
     * @return DeviceId(IMEI) = 99000311726612<br></br>
     * DeviceSoftwareVersion = 00<br></br>
     * Line1Number =<br></br>
     * NetworkCountryIso = cn<br></br>
     * NetworkOperator = 46003<br></br>
     * NetworkOperatorName = 中国电信<br></br>
     * NetworkType = 6<br></br>
     * PhoneType = 2<br></br>
     * SimCountryIso = cn<br></br>
     * SimOperator = 46003<br></br>
     * SimOperatorName = 中国电信<br></br>
     * SimSerialNumber = 89860315045710604022<br></br>
     * SimState = 5<br></br>
     * SubscriberId(IMSI) = 460030419724900<br></br>
     * VoiceMailNumber = *86<br></br>
     */
    val phoneStatus: String
        @SuppressLint("HardwareIds", "MissingPermission")
        get() {
            val tm = Utils.app.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
                    ?: return ""
            var str = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                str += "DeviceId(IMEI) = " + tm.imei + "\n"
            } else {
                str += "DeviceId(IMEI) = " + tm.deviceId + "\n"
            }
            str += "DeviceSoftwareVersion = " + tm.deviceSoftwareVersion + "\n"
            str += "Line1Number = " + tm.line1Number + "\n"
            str += "NetworkCountryIso = " + tm.networkCountryIso + "\n"
            str += "NetworkOperator = " + tm.networkOperator + "\n"
            str += "NetworkOperatorName = " + tm.networkOperatorName + "\n"
            str += "NetworkType = " + tm.networkType + "\n"
            str += "PhoneType = " + tm.phoneType + "\n"
            str += "SimCountryIso = " + tm.simCountryIso + "\n"
            str += "SimOperator = " + tm.simOperator + "\n"
            str += "SimOperatorName = " + tm.simOperatorName + "\n"
            str += "SimSerialNumber = " + tm.simSerialNumber + "\n"
            str += "SimState = " + tm.simState + "\n"
            str += "SubscriberId(IMSI) = " + tm.subscriberId + "\n"
            str += "VoiceMailNumber = " + tm.voiceMailNumber + "\n"
            return str
        }

    /**
     * 跳至拨号界面
     *
     * @param phoneNumber 电话号码
     */
    fun dial(phoneNumber: String) {
        Utils.app.startActivity(IntentUtils.getDialIntent(phoneNumber, true))
    }

    /**
     * 拨打电话
     *
     * 需添加权限 `<uses-permission android:name="android.permission.CALL_PHONE" />`
     *
     * @param phoneNumber 电话号码
     */
    fun call(phoneNumber: String) {
        Utils.app.startActivity(IntentUtils.getCallIntent(phoneNumber, true))
    }

    /**
     * 跳至发送短信界面
     *
     * @param phoneNumber 接收号码
     * @param content     短信内容
     */
    fun sendSms(phoneNumber: String, content: String) {
        Utils.app.startActivity(IntentUtils.getSendSmsIntent(phoneNumber, content, true))
    }

    /**
     * 发送短信
     *
     * 需添加权限 `<uses-permission android:name="android.permission.SEND_SMS" />`
     *
     * @param phoneNumber 接收号码
     * @param content     短信内容
     */
    fun sendSmsSilent(phoneNumber: String, content: String) {
        if (StringUtils.isEmpty(content)) return
        val sentIntent = PendingIntent.getBroadcast(Utils.app, 0, Intent(), 0)
        val smsManager = SmsManager.getDefault()
        if (content.length >= 70) {
            val ms = smsManager.divideMessage(content)
            for (str in ms) {
                smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null)
            }
        } else {
            smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null)
        }
    }

    /**
     * 获取手机联系人
     *
     * 需添加权限
     * `<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />`
     *
     * 需添加权限
     * `<uses-permission android:name="android.permission.READ_CONTACTS" />`
     */
    fun getAllContactInfo() {
        Log.i("PhoneUtils", "Please refer to the following code.")
        /*
    ArrayList<HashMap<String, String>> list = new ArrayList<>();
    // 1.获取内容解析者
    ContentResolver resolver = Utils.getApp().getContentResolver();
    // 2.获取内容提供者的地址:com.android.contacts
    // raw_contacts 表的地址 :raw_contacts
    // view_data 表的地址 : data
    // 3.生成查询地址
    Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
    Uri date_uri = Uri.parse("content://com.android.contacts/data");
    // 4.查询操作,先查询 raw_contacts,查询 contact_id
    // projection : 查询的字段
    Cursor cursor = resolver.query(raw_uri, new String[]{"contact_id"}, null, null, null);
    try {
        // 5.解析 cursor
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // 6.获取查询的数据
                String contact_id = cursor.getString(0);
                // cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex
                // : 查询字段在 cursor 中索引值,一般都是用在查询字段比较多的时候
                // 判断 contact_id 是否为空
                if (!StringUtils.isEmpty(contact_id)) {//null   ""
                    // 7.根据 contact_id 查询 view_data 表中的数据
                    // selection : 查询条件
                    // selectionArgs :查询条件的参数
                    // sortOrder : 排序
                    // 空指针: 1.null.方法 2.参数为 null
                    Cursor c = resolver.query(date_uri, new String[]{"data1",
                                    "mimetype"}, "raw_contact_id=?",
                            new String[]{contact_id}, null);
                    HashMap<String, String> map = new HashMap<String, String>();
                    // 8.解析 c
                    if (c != null) {
                        while (c.moveToNext()) {
                            // 9.获取数据
                            String data1 = c.getString(0);
                            String mimetype = c.getString(1);
                            // 10.根据类型去判断获取的 data1 数据并保存
                            if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                                // 电话
                                map.put("phone", data1);
                            } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                                // 姓名
                                map.put("name", data1);
                            }
                        }
                    }
                    // 11.添加到集合中数据
                    list.add(map);
                    // 12.关闭 cursor
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
    } finally {
        // 12.关闭 cursor
        if (cursor != null) {
            cursor.close();
        }
    }
    return list;
    */
    }

    /**
     * 打开手机联系人界面点击联系人后便获取该号码
     *
     * 参照以下注释代码
     */
    fun getContactNum() {
        Log.i("PhoneUtils", "Please refer to the following code.")
        /*
    Intent intent = new Intent();
    intent.setAction("android.intent.action.PICK");
    intent.setType("vnd.android.cursor.dir/phone_v2");
    startActivityForResult(intent, 0);

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Uri uri = data.getData();
            String num = null;
            // 创建内容解析者
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(uri,
                    null, null, null, null);
            while (cursor.moveToNext()) {
                num = cursor.getString(cursor.getColumnIndex("data1"));
            }
            cursor.close();
            num = num.replaceAll("-", "");//替换的操作,555-6 -> 5556
        }
    }
    */
    }

    /**
     * 获取手机短信并保存到 xml 中
     *
     * 需添加权限
     * `<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />`
     *
     * 需添加权限
     * `<uses-permission android:name="android.permission.READ_SMS" />`
     */
    fun getAllSMS() {
        Log.i("PhoneUtils", "Please refer to the following code.")
        /*
    // 1.获取短信
    // 1.1获取内容解析者
    ContentResolver resolver = Utils.getApp().getContentResolver();
    // 1.2获取内容提供者地址   sms,sms表的地址:null  不写
    // 1.3获取查询路径
    Uri uri = Uri.parse("content://sms");
    // 1.4.查询操作
    // projection : 查询的字段
    // selection : 查询的条件
    // selectionArgs : 查询条件的参数
    // sortOrder : 排序
    Cursor cursor = resolver.query(uri,
            new String[]{"address", "date", "type", "body"},
            null,
            null,
            null
    );
    // 设置最大进度
    int count = cursor.getCount();//获取短信的个数
    // 2.备份短信
    // 2.1获取xml序列器
    XmlSerializer xmlSerializer = Xml.newSerializer();
    try {
        // 2.2设置xml文件保存的路径
        // os : 保存的位置
        // encoding : 编码格式
        xmlSerializer.setOutput(new FileOutputStream(new File("/mnt/sdcard/backupsms.xml")),
                "utf-8"
        );
        // 2.3设置头信息
        // standalone : 是否独立保存
        xmlSerializer.startDocument("utf-8", true);
        // 2.4设置根标签
        xmlSerializer.startTag(null, "smss");
        // 1.5.解析cursor
        while (cursor.moveToNext()) {
            SystemClock.sleep(1000);
            // 2.5设置短信的标签
            xmlSerializer.startTag(null, "sms");
            // 2.6设置文本内容的标签
            xmlSerializer.startTag(null, "address");
            String address = cursor.getString(0);
            // 2.7设置文本内容
            xmlSerializer.text(address);
            xmlSerializer.endTag(null, "address");
            xmlSerializer.startTag(null, "date");
            String date = cursor.getString(1);
            xmlSerializer.text(date);
            xmlSerializer.endTag(null, "date");
            xmlSerializer.startTag(null, "type");
            String type = cursor.getString(2);
            xmlSerializer.text(type);
            xmlSerializer.endTag(null, "type");
            xmlSerializer.startTag(null, "body");
            String body = cursor.getString(3);
            xmlSerializer.text(body);
            xmlSerializer.endTag(null, "body");
            xmlSerializer.endTag(null, "sms");
            Log.i("PhoneUtils", "address: " + address
                    + ", date: " + date
                    + ", type: " + type
                    + ", body: " + body
            );
        }
        xmlSerializer.endTag(null, "smss");
        xmlSerializer.endDocument();
        // 2.8将数据刷新到文件中
        xmlSerializer.flush();
    } catch (Exception e) {
        e.printStackTrace();
    }
    */
    }
}

