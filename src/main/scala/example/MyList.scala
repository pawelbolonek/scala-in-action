package example.list

import scala.collection.mutable.ListBuffer;



class MyList() extends List{
    override val list: ListBuffer[Int] = new ListBuffer[Int]()
}

trait List {

    val list: ListBuffer[Int]
    def head = list.head
    def foo():Unit = println("Base trait")

}

trait WritableMyList extends List{
    def +=(item: Int) = list+=item
    override def foo():Unit = print("Writable trait")
}
trait SecondTraitMyList extends List{
    override def foo():Unit = print("Second trait")
}