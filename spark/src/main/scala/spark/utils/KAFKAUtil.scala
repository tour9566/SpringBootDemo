package spark.utils



import java.util

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.spark.rdd.RDD

class KAFKAUtil {

  def initKAfka(propName:String):util.HashMap[String, Object]={
    val props = new util.HashMap[String, Object]()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, PropertiesUtil.getStringByKey("default.brokers", propName))
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, PropertiesUtil.getStringByKey("default.key_serializer_class_config", propName))
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PropertiesUtil.getStringByKey("default.value_serializer_class_config", propName))
    props.put(ProducerConfig.BATCH_SIZE_CONFIG, PropertiesUtil.getStringByKey("default.batch_size_config", propName))
    props.put(ProducerConfig.LINGER_MS_CONFIG, PropertiesUtil.getStringByKey("default.linger_ms_config", propName))
    props;
  }

  def sendData(queryTopic:String, data:String,propName:String): Unit ={
    val props = initKAfka(propName)
    val kafkaProducer=new KafkaProducer[String,String](props)

    val record=new ProducerRecord[String,String](queryTopic,data)
    //4数据的发送
    kafkaProducer.send(record)
    kafkaProducer.close()
  }
}

object KAFKAUtil{
  def main(args: Array[String]): Unit = {
    val util1 = new KAFKAUtil
    for(i <- 1 to 1000){
      util1.sendData("kafka_test",i.toString,"kafkaConfig.properties")
    }

  }


}
