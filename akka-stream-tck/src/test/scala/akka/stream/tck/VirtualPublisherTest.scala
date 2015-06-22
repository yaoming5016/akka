/**
 * Copyright (C) 2015 Typesafe Inc. <http://www.typesafe.com>
 */
package akka.stream.tck

import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.Flow
import org.reactivestreams.Processor
import akka.stream.impl.VirtualProcessor

class VirtualProcessorTest extends AkkaIdentityProcessorVerification[Int] {

  override def createIdentityProcessor(maxBufferSize: Int): Processor[Int, Int] = {
    implicit val materializer = ActorFlowMaterializer()(system)

    val identity = processorFromFlow(Flow[Int].map(elem ⇒ elem).named("identity"))
    val left, right = new VirtualProcessor[Int]
    left.subscribe(identity)
    identity.subscribe(right)
    processorFromSubscriberAndPublisher(left, right)
  }

  override def createElement(element: Int): Int = element

}

class VirtualProcessorSingleTest extends AkkaIdentityProcessorVerification[Int] {

  override def createIdentityProcessor(maxBufferSize: Int): Processor[Int, Int] =
    new VirtualProcessor[Int]

  override def createElement(element: Int): Int = element

}