/*
 * Copyright (c) 2011-13 Miles Sabin 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package shapeless
package syntax
package std

/**
 * Conversions between `Iterables` and `HLists`.
 * 
 * The implicit defined by this object enhances `Iterables` with a `toHList` method which constructs an equivalently
 * typed [[shapeless.HList]] if possible. 
 * 
 * @author Miles Sabin
 * @author Rob Norris
 */
object iterable {
  implicit def traversableOps[T](t : T)(implicit ev: T => Iterable[_]) = new IterableOps(t)
  implicit def traversableOps2[CC[T] <: Iterable[T], A](as: CC[A]) = new IterableOps2(as)
}

final class IterableOps[T](t : T)(implicit ev: T => Iterable[_]) {
  import ops.iterable._

  def toHList[L <: HList](implicit fl : FromIterable[L]) : Option[L] = fl(t)
}

final class IterableOps2[CC[T] <: Iterable[T], A](as: CC[A]) {
  import ops.iterable._

  def toSizedHList(n: Nat)(implicit ts: ToSizedHList[CC, A, n.N]): ts.Out = ts(as)
}
