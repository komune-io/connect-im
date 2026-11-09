//region block: polyfills
if (typeof Math.imul === 'undefined') {
  Math.imul = function imul(a, b) {
    return (a & 4.29490176E9) * (b & 65535) + (a & 65535) * (b | 0) | 0;
  };
}
if (typeof ArrayBuffer.isView === 'undefined') {
  ArrayBuffer.isView = function (a) {
    return a != null && a.__proto__ != null && a.__proto__.__proto__ === Int8Array.prototype.__proto__;
  };
}
if (typeof Array.prototype.fill === 'undefined') {
  // Polyfill from https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/fill#Polyfill
  Object.defineProperty(Array.prototype, 'fill', {value: function (value) {
    // Steps 1-2.
    if (this == null) {
      throw new TypeError('this is null or not defined');
    }
    var O = Object(this); // Steps 3-5.
    var len = O.length >>> 0; // Steps 6-7.
    var start = arguments[1];
    var relativeStart = start >> 0; // Step 8.
    var k = relativeStart < 0 ? Math.max(len + relativeStart, 0) : Math.min(relativeStart, len); // Steps 9-10.
    var end = arguments[2];
    var relativeEnd = end === undefined ? len : end >> 0; // Step 11.
    var finalValue = relativeEnd < 0 ? Math.max(len + relativeEnd, 0) : Math.min(relativeEnd, len); // Step 12.
    while (k < finalValue) {
      O[k] = value;
      k++;
    }
    ; // Step 13.
    return O;
  }});
}
[Int8Array, Int16Array, Uint16Array, Int32Array, Float32Array, Float64Array].forEach(function (TypedArray) {
  if (typeof TypedArray.prototype.fill === 'undefined') {
    Object.defineProperty(TypedArray.prototype, 'fill', {value: Array.prototype.fill});
  }
});
if (typeof Math.clz32 === 'undefined') {
  Math.clz32 = function (log, LN2) {
    return function (x) {
      var asUint = x >>> 0;
      if (asUint === 0) {
        return 32;
      }
      return 31 - (log(asUint) / LN2 | 0) | 0; // the "| 0" acts like math.floor
    };
  }(Math.log, Math.LN2);
}
if (typeof Math.trunc === 'undefined') {
  Math.trunc = function (x) {
    if (isNaN(x)) {
      return NaN;
    }
    if (x > 0) {
      return Math.floor(x);
    }
    return Math.ceil(x);
  };
}
//endregion
//region block: imports
var imul_0 = Math.imul;
var trunc = Math.trunc;
var isView = ArrayBuffer.isView;
var clz32 = Math.clz32;
//endregion
//region block: pre-declaration
class CharSequence {}
class Number_0 {}
class Char {}
class Collection {}
class KtSet {}
class Companion {
  v(array) {
    // Inline function 'kotlin.js.collections.toList' call
    return createListFrom(array);
  }
}
class KtList {}
function asJsReadonlyArrayView() {
  return createJsReadonlyArrayViewFrom(this);
}
class Entry {}
class KtMap {}
class Companion_0 {}
class Enum {
  constructor(name, ordinal) {
    this.i1_1 = name;
    this.j1_1 = ordinal;
  }
  k1() {
    return this.i1_1;
  }
  l1() {
    return this.j1_1;
  }
  m1(other) {
    return compareTo(this.j1_1, other.j1_1);
  }
  n1(other) {
    return this.m1(other instanceof Enum ? other : THROW_CCE());
  }
  equals(other) {
    return this === other;
  }
  hashCode() {
    return identityHashCode(this);
  }
  toString() {
    return this.i1_1;
  }
}
class Long extends Number_0 {}
class FunctionAdapter {}
class arrayIterator$1 {
  constructor($array) {
    this.q1_1 = $array;
    this.p1_1 = 0;
  }
  g() {
    return !(this.p1_1 === this.q1_1.length);
  }
  h() {
    var tmp;
    if (!(this.p1_1 === this.q1_1.length)) {
      var _unary__edvuaz = this.p1_1;
      this.p1_1 = _unary__edvuaz + 1 | 0;
      tmp = this.q1_1[_unary__edvuaz];
    } else {
      throw NoSuchElementException.u1('' + this.p1_1);
    }
    return tmp;
  }
}
class JsArrayView extends Array {}
class StringCompanionObject {}
class Comparator {}
class Unit {
  toString() {
    return 'kotlin.Unit';
  }
}
class AbstractCollection {
  static s2($box) {
    return createThis(this, $box);
  }
  t(element) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'kotlin.collections.any' call
      var tmp;
      if (isInterface(this, Collection)) {
        tmp = this.s();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$0 = false;
        break $l$block_0;
      }
      var _iterator__ex2g4s = this.f();
      while (_iterator__ex2g4s.g()) {
        var element_0 = _iterator__ex2g4s.h();
        if (equals(element_0, element)) {
          tmp$ret$0 = true;
          break $l$block_0;
        }
      }
      tmp$ret$0 = false;
    }
    return tmp$ret$0;
  }
  u(elements) {
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'kotlin.collections.all' call
      var tmp;
      if (isInterface(elements, Collection)) {
        tmp = elements.s();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$0 = true;
        break $l$block_0;
      }
      var _iterator__ex2g4s = elements.f();
      while (_iterator__ex2g4s.g()) {
        var element = _iterator__ex2g4s.h();
        if (!this.t(element)) {
          tmp$ret$0 = false;
          break $l$block_0;
        }
      }
      tmp$ret$0 = true;
    }
    return tmp$ret$0;
  }
  s() {
    return this.i() === 0;
  }
  toString() {
    return joinToString_1(this, ', ', '[', ']', VOID, VOID, AbstractCollection$toString$lambda(this));
  }
  toArray() {
    return collectionToArray(this);
  }
}
class AbstractMutableCollection extends AbstractCollection {
  static r2() {
    return this.s2();
  }
  toJSON() {
    return this.toArray();
  }
  t2() {
  }
}
class IteratorImpl {
  constructor($outer, $box) {
    boxApply(this, $box);
    this.w2_1 = $outer;
    this.u2_1 = 0;
    this.v2_1 = -1;
  }
  g() {
    return this.u2_1 < this.w2_1.i();
  }
  h() {
    if (!this.g())
      throw NoSuchElementException.x2();
    var tmp = this;
    var _unary__edvuaz = this.u2_1;
    this.u2_1 = _unary__edvuaz + 1 | 0;
    tmp.v2_1 = _unary__edvuaz;
    return this.w2_1.w(this.v2_1);
  }
}
class ListIteratorImpl extends IteratorImpl {
  constructor($outer, index, $box) {
    if ($box === VOID)
      $box = {};
    $box.b3_1 = $outer;
    super($outer, $box);
    Companion_instance_3.d3(index, this.b3_1.i());
    this.u2_1 = index;
  }
  e3() {
    return this.u2_1 > 0;
  }
  f3() {
    return this.u2_1;
  }
  g3() {
    if (!this.e3())
      throw NoSuchElementException.x2();
    var tmp = this;
    this.u2_1 = this.u2_1 - 1 | 0;
    tmp.v2_1 = this.u2_1;
    return this.b3_1.w(this.v2_1);
  }
}
class AbstractMutableList extends AbstractMutableCollection {
  static n3() {
    var $this = this.r2();
    $this.m3_1 = 0;
    return $this;
  }
  o(element) {
    this.t2();
    this.p3(this.i(), element);
    return true;
  }
  f() {
    return new IteratorImpl(this);
  }
  t(element) {
    return this.x(element) >= 0;
  }
  x(element) {
    var tmp$ret$0;
    $l$block: {
      // Inline function 'kotlin.collections.indexOfFirst' call
      var index = 0;
      var _iterator__ex2g4s = this.f();
      while (_iterator__ex2g4s.g()) {
        var item = _iterator__ex2g4s.h();
        if (equals(item, element)) {
          tmp$ret$0 = index;
          break $l$block;
        }
        index = index + 1 | 0;
      }
      tmp$ret$0 = -1;
    }
    return tmp$ret$0;
  }
  y(element) {
    var tmp$ret$0;
    $l$block: {
      // Inline function 'kotlin.collections.indexOfLast' call
      var iterator = this.a1(this.i());
      while (iterator.e3()) {
        var it = iterator.g3();
        if (equals(it, element)) {
          tmp$ret$0 = iterator.f3();
          break $l$block;
        }
      }
      tmp$ret$0 = -1;
    }
    return tmp$ret$0;
  }
  z() {
    return this.a1(0);
  }
  a1(index) {
    return new ListIteratorImpl(this, index);
  }
  b1(fromIndex, toIndex) {
    return SubList.l3(this, fromIndex, toIndex);
  }
  equals(other) {
    if (other === this)
      return true;
    if (!(!(other == null) ? isInterface(other, KtList) : false))
      return false;
    return Companion_instance_3.r3(this, other);
  }
  hashCode() {
    return Companion_instance_3.s3(this);
  }
}
class SubList extends AbstractMutableList {
  constructor(list, fromIndex, toIndex) {
    return new.target.l3(list, fromIndex, toIndex);
  }
  static l3(list, fromIndex, toIndex) {
    var $this = this.n3();
    $this.i3_1 = list;
    $this.j3_1 = fromIndex;
    $this.k3_1 = 0;
    Companion_instance_3.o3($this.j3_1, toIndex, $this.i3_1.i());
    $this.k3_1 = toIndex - $this.j3_1 | 0;
    return $this;
  }
  p3(index, element) {
    Companion_instance_3.d3(index, this.k3_1);
    this.i3_1.p3(this.j3_1 + index | 0, element);
    this.k3_1 = this.k3_1 + 1 | 0;
  }
  w(index) {
    Companion_instance_3.q3(index, this.k3_1);
    return this.i3_1.w(this.j3_1 + index | 0);
  }
  i() {
    return this.k3_1;
  }
  t2() {
    return this.i3_1.t2();
  }
}
class AbstractMap {
  static a4() {
    var $this = createThis(this);
    $this.y3_1 = null;
    $this.z3_1 = null;
    return $this;
  }
  e1(key) {
    return !(implFindEntry(this, key) == null);
  }
  f4(entry) {
    if (!(!(entry == null) ? isInterface(entry, Entry) : false))
      return false;
    var key = entry.c1();
    var value = entry.d1();
    // Inline function 'kotlin.collections.get' call
    var ourValue = (isInterface(this, KtMap) ? this : THROW_CCE()).f1(key);
    if (!equals(value, ourValue)) {
      return false;
    }
    var tmp;
    if (ourValue == null) {
      // Inline function 'kotlin.collections.containsKey' call
      tmp = !(isInterface(this, KtMap) ? this : THROW_CCE()).e1(key);
    } else {
      tmp = false;
    }
    if (tmp) {
      return false;
    }
    return true;
  }
  equals(other) {
    if (other === this)
      return true;
    if (!(!(other == null) ? isInterface(other, KtMap) : false))
      return false;
    if (!(this.i() === other.i()))
      return false;
    var tmp0 = other.h1();
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'kotlin.collections.all' call
      var tmp;
      if (isInterface(tmp0, Collection)) {
        tmp = tmp0.s();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$0 = true;
        break $l$block_0;
      }
      var _iterator__ex2g4s = tmp0.f();
      while (_iterator__ex2g4s.g()) {
        var element = _iterator__ex2g4s.h();
        if (!this.f4(element)) {
          tmp$ret$0 = false;
          break $l$block_0;
        }
      }
      tmp$ret$0 = true;
    }
    return tmp$ret$0;
  }
  f1(key) {
    var tmp0_safe_receiver = implFindEntry(this, key);
    return tmp0_safe_receiver == null ? null : tmp0_safe_receiver.d1();
  }
  hashCode() {
    return hashCode(this.h1());
  }
  s() {
    return this.i() === 0;
  }
  i() {
    return this.h1().i();
  }
  g1() {
    if (this.y3_1 == null) {
      var tmp = this;
      tmp.y3_1 = AbstractMap$keys$1.ka(this);
    }
    return ensureNotNull(this.y3_1);
  }
  toString() {
    var tmp = this.h1();
    return joinToString_1(tmp, ', ', '{', '}', VOID, VOID, AbstractMap$toString$lambda(this));
  }
}
class AbstractMutableMap extends AbstractMap {
  static x3() {
    var $this = this.a4();
    $this.v3_1 = null;
    $this.w3_1 = null;
    return $this;
  }
  b4() {
    return HashMapKeysDefault.d4(this);
  }
  g1() {
    var tmp0_elvis_lhs = this.v3_1;
    var tmp;
    if (tmp0_elvis_lhs == null) {
      // Inline function 'kotlin.also' call
      var this_0 = this.b4();
      this.v3_1 = this_0;
      tmp = this_0;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    return tmp;
  }
}
class AbstractMutableSet extends AbstractMutableCollection {
  static g4() {
    return this.r2();
  }
  equals(other) {
    if (other === this)
      return true;
    if (!(!(other == null) ? isInterface(other, KtSet) : false))
      return false;
    return Companion_instance_5.h4(this, other);
  }
  hashCode() {
    return Companion_instance_5.i4(this);
  }
}
class Companion_1 {
  constructor() {
    Companion_instance_1 = this;
    var tmp = this;
    // Inline function 'kotlin.also' call
    var this_0 = ArrayList.m(0);
    this_0.l_1 = true;
    tmp.j4_1 = this_0;
  }
}
class ArrayList extends AbstractMutableList {
  constructor(array) {
    return new.target.v1(array);
  }
  static v1(array) {
    Companion_getInstance_1();
    var $this = this.n3();
    $this.k_1 = array;
    $this.l_1 = false;
    return $this;
  }
  static k4() {
    Companion_getInstance_1();
    // Inline function 'kotlin.emptyArray' call
    var tmp$ret$0 = [];
    return this.v1(tmp$ret$0);
  }
  static m(initialCapacity) {
    Companion_getInstance_1();
    // Inline function 'kotlin.emptyArray' call
    var tmp$ret$0 = [];
    var $this = this.v1(tmp$ret$0);
    // Inline function 'kotlin.require' call
    if (!(initialCapacity >= 0)) {
      var message = 'Negative initial capacity: ' + initialCapacity;
      throw IllegalArgumentException.q2(toString_1(message));
    }
    return $this;
  }
  w1() {
    this.t2();
    this.l_1 = true;
    return this.i() > 0 ? this : Companion_getInstance_1().j4_1;
  }
  i() {
    return this.k_1.length;
  }
  w(index) {
    return this.k_1[rangeCheck(this, index)];
  }
  o(element) {
    this.t2();
    // Inline function 'kotlin.js.asDynamic' call
    this.k_1.push(element);
    this.m3_1 = this.m3_1 + 1 | 0;
    return true;
  }
  p3(index, element) {
    this.t2();
    // Inline function 'kotlin.js.asDynamic' call
    this.k_1.splice(insertionRangeCheck(this, index), 0, element);
    this.m3_1 = this.m3_1 + 1 | 0;
  }
  n(elements) {
    this.t2();
    if (elements.s())
      return false;
    var offset = increaseLength(this, elements.i());
    // Inline function 'kotlin.collections.forEachIndexed' call
    var index = 0;
    var _iterator__ex2g4s = elements.f();
    while (_iterator__ex2g4s.g()) {
      var item = _iterator__ex2g4s.h();
      var _unary__edvuaz = index;
      index = _unary__edvuaz + 1 | 0;
      var index_0 = checkIndexOverflow(_unary__edvuaz);
      this.k_1[offset + index_0 | 0] = item;
    }
    this.m3_1 = this.m3_1 + 1 | 0;
    return true;
  }
  x(element) {
    return indexOf(this.k_1, element);
  }
  y(element) {
    return lastIndexOf(this.k_1, element);
  }
  toString() {
    return arrayToString(this.k_1);
  }
  l4() {
    return [].slice.call(this.k_1);
  }
  toArray() {
    return this.l4();
  }
  t2() {
    if (this.l_1)
      throw UnsupportedOperationException.a2();
  }
}
class HashMap extends AbstractMutableMap {
  static s4(internalMap) {
    var $this = this.x3();
    init_kotlin_collections_HashMap($this);
    $this.q4_1 = internalMap;
    return $this;
  }
  static t4() {
    return this.s4(InternalHashMap.e5());
  }
  static f5(initialCapacity, loadFactor) {
    return this.s4(InternalHashMap.g5(initialCapacity, loadFactor));
  }
  static h5(initialCapacity) {
    return this.f5(initialCapacity, 1.0);
  }
  e1(key) {
    return this.q4_1.i5(key);
  }
  b4() {
    return HashMapKeys.k5(this.q4_1);
  }
  h1() {
    var tmp0_elvis_lhs = this.r4_1;
    var tmp;
    if (tmp0_elvis_lhs == null) {
      // Inline function 'kotlin.also' call
      var this_0 = HashMapEntrySet.m5(this.q4_1);
      this.r4_1 = this_0;
      tmp = this_0;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    return tmp;
  }
  f1(key) {
    return this.q4_1.f1(key);
  }
  e4(key, value) {
    return this.q4_1.e4(key, value);
  }
  i() {
    return this.q4_1.i();
  }
}
class HashMapKeys extends AbstractMutableSet {
  constructor(backing) {
    return new.target.k5(backing);
  }
  static k5(backing) {
    var $this = this.g4();
    $this.j5_1 = backing;
    return $this;
  }
  i() {
    return this.j5_1.i();
  }
  s() {
    return this.j5_1.i() === 0;
  }
  t(element) {
    return this.j5_1.i5(element);
  }
  o(element) {
    throw UnsupportedOperationException.a2();
  }
  f() {
    return this.j5_1.n5();
  }
}
class HashMapEntrySetBase extends AbstractMutableSet {
  static p5(backing) {
    var $this = this.g4();
    $this.o5_1 = backing;
    return $this;
  }
  i() {
    return this.o5_1.i();
  }
  s() {
    return this.o5_1.i() === 0;
  }
  r5(element) {
    return this.o5_1.t5(element);
  }
  t(element) {
    if (!(!(element == null) ? isInterface(element, Entry) : false))
      return false;
    return this.r5((!(element == null) ? isInterface(element, Entry) : false) ? element : THROW_CCE());
  }
  s5(element) {
    throw UnsupportedOperationException.a2();
  }
  o(element) {
    return this.s5((!(element == null) ? isInterface(element, Entry) : false) ? element : THROW_CCE());
  }
  u(elements) {
    return this.o5_1.u5(elements);
  }
}
class HashMapEntrySet extends HashMapEntrySetBase {
  constructor(backing) {
    return new.target.m5(backing);
  }
  static m5(backing) {
    return this.p5(backing);
  }
  f() {
    return this.o5_1.q5();
  }
}
class HashMapKeysDefault$iterator$1 {
  constructor($entryIterator) {
    this.v5_1 = $entryIterator;
  }
  g() {
    return this.v5_1.g();
  }
  h() {
    return this.v5_1.h().c1();
  }
}
class HashMapKeysDefault extends AbstractMutableSet {
  constructor(backingMap) {
    return new.target.d4(backingMap);
  }
  static d4(backingMap) {
    var $this = this.g4();
    $this.c4_1 = backingMap;
    return $this;
  }
  w5(element) {
    throw UnsupportedOperationException.x5('Add is not supported on keys');
  }
  o(element) {
    return this.w5(element);
  }
  i5(element) {
    return this.c4_1.e1(element);
  }
  t(element) {
    if (!true)
      return false;
    return this.i5(element);
  }
  f() {
    var entryIterator = this.c4_1.h1().f();
    return new HashMapKeysDefault$iterator$1(entryIterator);
  }
  i() {
    return this.c4_1.i();
  }
}
class HashSet extends AbstractMutableSet {
  static y5(map) {
    var $this = this.g4();
    init_kotlin_collections_HashSet($this);
    $this.p_1 = map;
    return $this;
  }
  static z5() {
    return this.y5(InternalHashMap.e5());
  }
  static a6(initialCapacity, loadFactor) {
    return this.y5(InternalHashMap.g5(initialCapacity, loadFactor));
  }
  static q(initialCapacity) {
    return this.a6(initialCapacity, 1.0);
  }
  o(element) {
    return this.p_1.e4(element, true) == null;
  }
  t(element) {
    return this.p_1.i5(element);
  }
  s() {
    return this.p_1.i() === 0;
  }
  f() {
    return this.p_1.n5();
  }
  i() {
    return this.p_1.i();
  }
}
class Companion_2 {
  constructor() {
    this.n6_1 = -1640531527;
    this.o6_1 = 8;
    this.p6_1 = 2;
    this.q6_1 = -1;
  }
}
class Itr {
  constructor(map) {
    this.r6_1 = map;
    this.s6_1 = 0;
    this.t6_1 = -1;
    this.u6_1 = this.r6_1.b5_1;
    this.v6();
  }
  v6() {
    while (this.s6_1 < this.r6_1.z4_1 && this.r6_1.w4_1[this.s6_1] < 0) {
      this.s6_1 = this.s6_1 + 1 | 0;
    }
  }
  g() {
    return this.s6_1 < this.r6_1.z4_1;
  }
  w6() {
    if (!(this.r6_1.b5_1 === this.u6_1))
      throw ConcurrentModificationException.x6();
  }
}
class KeysItr extends Itr {
  h() {
    this.w6();
    if (this.s6_1 >= this.r6_1.z4_1)
      throw NoSuchElementException.x2();
    var tmp = this;
    var _unary__edvuaz = this.s6_1;
    this.s6_1 = _unary__edvuaz + 1 | 0;
    tmp.t6_1 = _unary__edvuaz;
    var result = this.r6_1.u4_1[this.t6_1];
    this.v6();
    return result;
  }
}
class EntriesItr extends Itr {
  h() {
    this.w6();
    if (this.s6_1 >= this.r6_1.z4_1)
      throw NoSuchElementException.x2();
    var tmp = this;
    var _unary__edvuaz = this.s6_1;
    this.s6_1 = _unary__edvuaz + 1 | 0;
    tmp.t6_1 = _unary__edvuaz;
    var result = new EntryRef(this.r6_1, this.t6_1);
    this.v6();
    return result;
  }
  g7() {
    if (this.s6_1 >= this.r6_1.z4_1)
      throw NoSuchElementException.x2();
    var tmp = this;
    var _unary__edvuaz = this.s6_1;
    this.s6_1 = _unary__edvuaz + 1 | 0;
    tmp.t6_1 = _unary__edvuaz;
    // Inline function 'kotlin.hashCode' call
    var tmp0_safe_receiver = this.r6_1.u4_1[this.t6_1];
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : hashCode(tmp0_safe_receiver);
    var tmp_0 = tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
    // Inline function 'kotlin.hashCode' call
    var tmp0_safe_receiver_0 = ensureNotNull(this.r6_1.v4_1)[this.t6_1];
    var tmp1_elvis_lhs_0 = tmp0_safe_receiver_0 == null ? null : hashCode(tmp0_safe_receiver_0);
    var result = tmp_0 ^ (tmp1_elvis_lhs_0 == null ? 0 : tmp1_elvis_lhs_0);
    this.v6();
    return result;
  }
  h7(sb) {
    if (this.s6_1 >= this.r6_1.z4_1)
      throw NoSuchElementException.x2();
    var tmp = this;
    var _unary__edvuaz = this.s6_1;
    this.s6_1 = _unary__edvuaz + 1 | 0;
    tmp.t6_1 = _unary__edvuaz;
    var key = this.r6_1.u4_1[this.t6_1];
    if (equals(key, this.r6_1))
      sb.j7('(this Map)');
    else
      sb.i7(key);
    sb.k7(_Char___init__impl__6a9atx(61));
    var value = ensureNotNull(this.r6_1.v4_1)[this.t6_1];
    if (equals(value, this.r6_1))
      sb.j7('(this Map)');
    else
      sb.i7(value);
    this.v6();
  }
}
class EntryRef {
  constructor(map, index) {
    this.f6_1 = map;
    this.g6_1 = index;
    this.h6_1 = this.f6_1.b5_1;
  }
  c1() {
    checkForComodification(this);
    return this.f6_1.u4_1[this.g6_1];
  }
  d1() {
    checkForComodification(this);
    return ensureNotNull(this.f6_1.v4_1)[this.g6_1];
  }
  equals(other) {
    var tmp;
    var tmp_0;
    if (!(other == null) ? isInterface(other, Entry) : false) {
      tmp_0 = equals(other.c1(), this.c1());
    } else {
      tmp_0 = false;
    }
    if (tmp_0) {
      tmp = equals(other.d1(), this.d1());
    } else {
      tmp = false;
    }
    return tmp;
  }
  hashCode() {
    // Inline function 'kotlin.hashCode' call
    var tmp0_safe_receiver = this.c1();
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : hashCode(tmp0_safe_receiver);
    var tmp = tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
    // Inline function 'kotlin.hashCode' call
    var tmp0_safe_receiver_0 = this.d1();
    var tmp1_elvis_lhs_0 = tmp0_safe_receiver_0 == null ? null : hashCode(tmp0_safe_receiver_0);
    return tmp ^ (tmp1_elvis_lhs_0 == null ? 0 : tmp1_elvis_lhs_0);
  }
  toString() {
    return toString_0(this.c1()) + '=' + toString_0(this.d1());
  }
}
class InternalMap {}
function containsAllEntries(m) {
  var tmp$ret$0;
  $l$block_0: {
    // Inline function 'kotlin.collections.all' call
    var tmp;
    if (isInterface(m, Collection)) {
      tmp = m.s();
    } else {
      tmp = false;
    }
    if (tmp) {
      tmp$ret$0 = true;
      break $l$block_0;
    }
    var _iterator__ex2g4s = m.f();
    while (_iterator__ex2g4s.g()) {
      var element = _iterator__ex2g4s.h();
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'kotlin.js.asDynamic' call
      var entry = element;
      var tmp_0;
      if (!(entry == null) ? isInterface(entry, Entry) : false) {
        tmp_0 = this.o7(entry);
      } else {
        tmp_0 = false;
      }
      if (!tmp_0) {
        tmp$ret$0 = false;
        break $l$block_0;
      }
    }
    tmp$ret$0 = true;
  }
  return tmp$ret$0;
}
class InternalHashMap {
  constructor(keysArray, valuesArray, presenceArray, hashArray, maxProbeDistance, length) {
    return new.target.l7(keysArray, valuesArray, presenceArray, hashArray, maxProbeDistance, length);
  }
  static l7(keysArray, valuesArray, presenceArray, hashArray, maxProbeDistance, length) {
    var $this = createThis(this);
    $this.u4_1 = keysArray;
    $this.v4_1 = valuesArray;
    $this.w4_1 = presenceArray;
    $this.x4_1 = hashArray;
    $this.y4_1 = maxProbeDistance;
    $this.z4_1 = length;
    $this.a5_1 = computeShift(Companion_instance_2, _get_hashSize__tftcho($this));
    $this.b5_1 = 0;
    $this.c5_1 = 0;
    $this.d5_1 = false;
    return $this;
  }
  i() {
    return this.c5_1;
  }
  static e5() {
    return this.m7(8);
  }
  static m7(initialCapacity) {
    return this.l7(arrayOfUninitializedElements(initialCapacity), null, new Int32Array(initialCapacity), new Int32Array(computeHashSize(Companion_instance_2, initialCapacity)), 2, 0);
  }
  static g5(initialCapacity, loadFactor) {
    var $this = this.m7(initialCapacity);
    // Inline function 'kotlin.require' call
    if (!(loadFactor > 0)) {
      var message = 'Non-positive load factor: ' + loadFactor;
      throw IllegalArgumentException.q2(toString_1(message));
    }
    return $this;
  }
  f1(key) {
    var index = findKey(this, key);
    if (index < 0)
      return null;
    return ensureNotNull(this.v4_1)[index];
  }
  i5(key) {
    return findKey(this, key) >= 0;
  }
  e4(key, value) {
    var index = addKey(this, key);
    var valuesArray = allocateValuesArray(this);
    if (index < 0) {
      var oldValue = valuesArray[(-index | 0) - 1 | 0];
      valuesArray[(-index | 0) - 1 | 0] = value;
      return oldValue;
    } else {
      valuesArray[index] = value;
      return null;
    }
  }
  equals(other) {
    var tmp;
    if (other === this) {
      tmp = true;
    } else {
      var tmp_0;
      if (!(other == null) ? isInterface(other, KtMap) : false) {
        tmp_0 = contentEquals_0(this, other);
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    }
    return tmp;
  }
  hashCode() {
    var result = 0;
    var it = this.q5();
    while (it.g()) {
      result = result + it.g7() | 0;
    }
    return result;
  }
  toString() {
    var sb = StringBuilder.n7(2 + imul_0(this.c5_1, 3) | 0);
    sb.j7('{');
    var i = 0;
    var it = this.q5();
    while (it.g()) {
      if (i > 0) {
        sb.j7(', ');
      }
      it.h7(sb);
      i = i + 1 | 0;
    }
    sb.j7('}');
    return sb.toString();
  }
  m6() {
    if (this.d5_1)
      throw UnsupportedOperationException.a2();
  }
  t5(entry) {
    var index = findKey(this, entry.c1());
    if (index < 0)
      return false;
    return equals(ensureNotNull(this.v4_1)[index], entry.d1());
  }
  o7(entry) {
    return this.t5(isInterface(entry, Entry) ? entry : THROW_CCE());
  }
  n5() {
    return new KeysItr(this);
  }
  q5() {
    return new EntriesItr(this);
  }
}
class LinkedHashMap extends HashMap {
  static v7() {
    var $this = this.t4();
    init_kotlin_collections_LinkedHashMap($this);
    return $this;
  }
  static w7(initialCapacity) {
    var $this = this.h5(initialCapacity);
    init_kotlin_collections_LinkedHashMap($this);
    return $this;
  }
}
class Exception extends Error {
  static e8() {
    var $this = createExternalThis(this, Error, [null]);
    setPropertiesToThrowableInstance($this);
    init_kotlin_Exception($this);
    return $this;
  }
  static f8(message) {
    var $this = createExternalThis(this, Error, [message]);
    setPropertiesToThrowableInstance($this, message);
    init_kotlin_Exception($this);
    return $this;
  }
  static g8(message, cause) {
    var $this = createExternalThis(this, Error, [message, setupCauseParameter(cause)]);
    setPropertiesToThrowableInstance($this, message, cause);
    init_kotlin_Exception($this);
    return $this;
  }
}
class RuntimeException extends Exception {
  static x7() {
    var $this = this.e8();
    init_kotlin_RuntimeException($this);
    return $this;
  }
  static k6(message) {
    var $this = this.f8(message);
    init_kotlin_RuntimeException($this);
    return $this;
  }
  static c8(message, cause) {
    var $this = this.g8(message, cause);
    init_kotlin_RuntimeException($this);
    return $this;
  }
}
class UnsupportedOperationException extends RuntimeException {
  static a2() {
    var $this = this.x7();
    init_kotlin_UnsupportedOperationException($this);
    return $this;
  }
  static x5(message) {
    var $this = this.k6(message);
    init_kotlin_UnsupportedOperationException($this);
    return $this;
  }
}
class IllegalArgumentException extends RuntimeException {
  static a8() {
    var $this = this.x7();
    init_kotlin_IllegalArgumentException($this);
    return $this;
  }
  static q2(message) {
    var $this = this.k6(message);
    init_kotlin_IllegalArgumentException($this);
    return $this;
  }
  static b8(message, cause) {
    var $this = this.c8(message, cause);
    init_kotlin_IllegalArgumentException($this);
    return $this;
  }
}
class NoSuchElementException extends RuntimeException {
  static x2() {
    var $this = this.x7();
    init_kotlin_NoSuchElementException($this);
    return $this;
  }
  static u1(message) {
    var $this = this.k6(message);
    init_kotlin_NoSuchElementException($this);
    return $this;
  }
}
class IndexOutOfBoundsException extends RuntimeException {
  static k8() {
    var $this = this.x7();
    init_kotlin_IndexOutOfBoundsException($this);
    return $this;
  }
  static l8(message) {
    var $this = this.k6(message);
    init_kotlin_IndexOutOfBoundsException($this);
    return $this;
  }
}
class IllegalStateException extends RuntimeException {
  static m8() {
    var $this = this.x7();
    init_kotlin_IllegalStateException($this);
    return $this;
  }
  static e2(message) {
    var $this = this.k6(message);
    init_kotlin_IllegalStateException($this);
    return $this;
  }
}
class ConcurrentModificationException extends RuntimeException {
  static x6() {
    var $this = this.x7();
    init_kotlin_ConcurrentModificationException($this);
    return $this;
  }
  static e6(message) {
    var $this = this.k6(message);
    init_kotlin_ConcurrentModificationException($this);
    return $this;
  }
}
class ArithmeticException extends RuntimeException {
  static q8() {
    var $this = this.x7();
    init_kotlin_ArithmeticException($this);
    return $this;
  }
  static r8(message) {
    var $this = this.k6(message);
    init_kotlin_ArithmeticException($this);
    return $this;
  }
}
class NullPointerException extends RuntimeException {
  static i2() {
    var $this = this.x7();
    init_kotlin_NullPointerException($this);
    return $this;
  }
}
class ClassCastException extends RuntimeException {
  static m2() {
    var $this = this.x7();
    init_kotlin_ClassCastException($this);
    return $this;
  }
}
class KClass {}
class KClassImpl {
  equals(other) {
    var tmp;
    if (other instanceof NothingKClassImpl) {
      tmp = false;
    } else {
      if (other instanceof KClassImpl) {
        tmp = (equals(this.w8(), other.w8()) && this.s8() == other.s8());
      } else {
        tmp = false;
      }
    }
    return tmp;
  }
  hashCode() {
    var tmp0_safe_receiver = this.s8();
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : getStringHashCode(tmp0_safe_receiver);
    return tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
  }
  toString() {
    return 'class ' + this.s8();
  }
}
class PrimitiveKClassImpl extends KClassImpl {
  constructor(jClass, simpleName, isInstanceFunction) {
    super();
    this.t8_1 = jClass;
    this.u8_1 = simpleName;
    this.v8_1 = isInstanceFunction;
  }
  w8() {
    return this.t8_1;
  }
  s8() {
    return this.u8_1;
  }
}
class NothingKClassImpl extends KClassImpl {
  constructor() {
    NothingKClassImpl_instance = null;
    super();
    NothingKClassImpl_instance = this;
    this.x8_1 = 'Nothing';
  }
  s8() {
    return this.x8_1;
  }
  w8() {
    throw UnsupportedOperationException.x5("There's no native JS class for Nothing type");
  }
  equals(other) {
    return other === this;
  }
  hashCode() {
    return 0;
  }
}
class SimpleKClassImpl extends KClassImpl {
  constructor(jClass) {
    super();
    this.y8_1 = jClass;
    var tmp = this;
    // Inline function 'kotlin.js.asDynamic' call
    var tmp0_safe_receiver = this.y8_1.$metadata$;
    // Inline function 'kotlin.js.unsafeCast' call
    tmp.z8_1 = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.simpleName;
  }
  w8() {
    return this.y8_1;
  }
  s8() {
    return this.z8_1;
  }
}
class KProperty1 {}
class PrimitiveClasses {
  constructor() {
    PrimitiveClasses_instance = this;
    var tmp = this;
    var tmp_0 = Object;
    tmp.anyClass = new PrimitiveKClassImpl(tmp_0, 'Any', PrimitiveClasses$anyClass$lambda);
    var tmp_1 = this;
    var tmp_2 = Number;
    tmp_1.numberClass = new PrimitiveKClassImpl(tmp_2, 'Number', PrimitiveClasses$numberClass$lambda);
    this.nothingClass = NothingKClassImpl_getInstance();
    var tmp_3 = this;
    var tmp_4 = Boolean;
    tmp_3.booleanClass = new PrimitiveKClassImpl(tmp_4, 'Boolean', PrimitiveClasses$booleanClass$lambda);
    var tmp_5 = this;
    var tmp_6 = Number;
    tmp_5.byteClass = new PrimitiveKClassImpl(tmp_6, 'Byte', PrimitiveClasses$byteClass$lambda);
    var tmp_7 = this;
    var tmp_8 = Number;
    tmp_7.shortClass = new PrimitiveKClassImpl(tmp_8, 'Short', PrimitiveClasses$shortClass$lambda);
    var tmp_9 = this;
    var tmp_10 = Number;
    tmp_9.intClass = new PrimitiveKClassImpl(tmp_10, 'Int', PrimitiveClasses$intClass$lambda);
    var tmp_11 = this;
    var tmp_12 = BigInt;
    tmp_11.longClass = new PrimitiveKClassImpl(tmp_12, 'Long', PrimitiveClasses$longClass$lambda);
    var tmp_13 = this;
    var tmp_14 = Number;
    tmp_13.floatClass = new PrimitiveKClassImpl(tmp_14, 'Float', PrimitiveClasses$floatClass$lambda);
    var tmp_15 = this;
    var tmp_16 = Number;
    tmp_15.doubleClass = new PrimitiveKClassImpl(tmp_16, 'Double', PrimitiveClasses$doubleClass$lambda);
    var tmp_17 = this;
    var tmp_18 = Array;
    tmp_17.arrayClass = new PrimitiveKClassImpl(tmp_18, 'Array', PrimitiveClasses$arrayClass$lambda);
    var tmp_19 = this;
    var tmp_20 = String;
    tmp_19.stringClass = new PrimitiveKClassImpl(tmp_20, 'String', PrimitiveClasses$stringClass$lambda);
    var tmp_21 = this;
    var tmp_22 = Error;
    tmp_21.throwableClass = new PrimitiveKClassImpl(tmp_22, 'Throwable', PrimitiveClasses$throwableClass$lambda);
    var tmp_23 = this;
    var tmp_24 = Array;
    tmp_23.booleanArrayClass = new PrimitiveKClassImpl(tmp_24, 'BooleanArray', PrimitiveClasses$booleanArrayClass$lambda);
    var tmp_25 = this;
    var tmp_26 = Uint16Array;
    tmp_25.charArrayClass = new PrimitiveKClassImpl(tmp_26, 'CharArray', PrimitiveClasses$charArrayClass$lambda);
    var tmp_27 = this;
    var tmp_28 = Int8Array;
    tmp_27.byteArrayClass = new PrimitiveKClassImpl(tmp_28, 'ByteArray', PrimitiveClasses$byteArrayClass$lambda);
    var tmp_29 = this;
    var tmp_30 = Int16Array;
    tmp_29.shortArrayClass = new PrimitiveKClassImpl(tmp_30, 'ShortArray', PrimitiveClasses$shortArrayClass$lambda);
    var tmp_31 = this;
    var tmp_32 = Int32Array;
    tmp_31.intArrayClass = new PrimitiveKClassImpl(tmp_32, 'IntArray', PrimitiveClasses$intArrayClass$lambda);
    var tmp_33 = this;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp_34 = typeof BigInt === 'undefined' ? VOID : BigInt;
    tmp_33.bigIntClass = new PrimitiveKClassImpl(tmp_34, 'BigInt', PrimitiveClasses$bigintClass$lambda);
    var tmp_35 = this;
    var tmp_36 = BigInt64Array;
    tmp_35.longArrayClass = new PrimitiveKClassImpl(tmp_36, 'LongArray', PrimitiveClasses$longArrayClass$lambda);
    var tmp_37 = this;
    var tmp_38 = Float32Array;
    tmp_37.floatArrayClass = new PrimitiveKClassImpl(tmp_38, 'FloatArray', PrimitiveClasses$floatArrayClass$lambda);
    var tmp_39 = this;
    var tmp_40 = Float64Array;
    tmp_39.doubleArrayClass = new PrimitiveKClassImpl(tmp_40, 'DoubleArray', PrimitiveClasses$doubleArrayClass$lambda);
  }
  a9() {
    return this.anyClass;
  }
  b9() {
    return this.numberClass;
  }
  c9() {
    return this.nothingClass;
  }
  d9() {
    return this.booleanClass;
  }
  e9() {
    return this.byteClass;
  }
  f9() {
    return this.shortClass;
  }
  g9() {
    return this.intClass;
  }
  h9() {
    return this.longClass;
  }
  i9() {
    return this.floatClass;
  }
  j9() {
    return this.doubleClass;
  }
  k9() {
    return this.arrayClass;
  }
  l9() {
    return this.stringClass;
  }
  m9() {
    return this.throwableClass;
  }
  n9() {
    return this.booleanArrayClass;
  }
  o9() {
    return this.charArrayClass;
  }
  p9() {
    return this.byteArrayClass;
  }
  q9() {
    return this.shortArrayClass;
  }
  r9() {
    return this.intArrayClass;
  }
  s9() {
    return this.bigIntClass;
  }
  t9() {
    return this.longArrayClass;
  }
  u9() {
    return this.floatArrayClass;
  }
  v9() {
    return this.doubleArrayClass;
  }
  functionClass(arity) {
    var tmp0_elvis_lhs = get_functionClasses()[arity];
    var tmp;
    if (tmp0_elvis_lhs == null) {
      // Inline function 'kotlin.run' call
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'kotlin.js.asDynamic' call
      var tmp_0 = Function;
      var tmp_1 = 'Function' + arity;
      var result = new PrimitiveKClassImpl(tmp_0, tmp_1, PrimitiveClasses$functionClass$lambda(arity));
      // Inline function 'kotlin.js.asDynamic' call
      get_functionClasses()[arity] = result;
      tmp = result;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    return tmp;
  }
}
class CharacterCodingException extends Exception {
  constructor(message) {
    return new.target.y9(message);
  }
  static y9(message) {
    var $this = this.f8(message);
    captureStack($this, $this.x9_1);
    return $this;
  }
  static z9() {
    return this.y9(null);
  }
}
class StringBuilder {
  constructor(content) {
    return new.target.aa(content);
  }
  static aa(content) {
    var $this = createThis(this);
    $this.c_1 = content;
    return $this;
  }
  static n7(capacity) {
    return this.d();
  }
  static d() {
    return this.aa('');
  }
  a() {
    // Inline function 'kotlin.js.asDynamic' call
    return this.c_1.length;
  }
  b(index) {
    // Inline function 'kotlin.text.getOrElse' call
    var this_0 = this.c_1;
    var tmp;
    if (0 <= index ? index <= (charSequenceLength(this_0) - 1 | 0) : false) {
      tmp = charSequenceGet(this_0, index);
    } else {
      throw IndexOutOfBoundsException.l8('index: ' + index + ', length: ' + this.a() + '}');
    }
    return tmp;
  }
  k7(value) {
    this.c_1 = this.c_1 + toString(value);
    return this;
  }
  e(value) {
    this.c_1 = this.c_1 + toString_0(value);
    return this;
  }
  i7(value) {
    this.c_1 = this.c_1 + toString_0(value);
    return this;
  }
  ba(value) {
    return this.j7(value.toString());
  }
  j7(value) {
    var tmp = this;
    var tmp_0 = this.c_1;
    tmp.c_1 = tmp_0 + (value == null ? 'null' : value);
    return this;
  }
  toString() {
    return this.c_1;
  }
  ca(index) {
    Companion_instance_3.q3(index, this.a());
    this.c_1 = substring(this.c_1, 0, index) + substring_0(this.c_1, index + 1 | 0);
    return this;
  }
}
class sam$kotlin_Comparator$0 {
  constructor(function_0) {
    this.da_1 = function_0;
  }
  ea(a, b) {
    return this.da_1(a, b);
  }
  compare(a, b) {
    return this.ea(a, b);
  }
  o1() {
    return this.da_1;
  }
  equals(other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.o1(), other.o1());
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  }
  hashCode() {
    return hashCode(this.o1());
  }
}
class Companion_3 {
  constructor() {
    this.c3_1 = 2147483639;
  }
  q3(index, size) {
    if (index < 0 || index >= size) {
      throw IndexOutOfBoundsException.l8('index: ' + index + ', size: ' + size);
    }
  }
  d3(index, size) {
    if (index < 0 || index > size) {
      throw IndexOutOfBoundsException.l8('index: ' + index + ', size: ' + size);
    }
  }
  o3(fromIndex, toIndex, size) {
    if (fromIndex < 0 || toIndex > size) {
      throw IndexOutOfBoundsException.l8('fromIndex: ' + fromIndex + ', toIndex: ' + toIndex + ', size: ' + size);
    }
    if (fromIndex > toIndex) {
      throw IllegalArgumentException.q2('fromIndex: ' + fromIndex + ' > toIndex: ' + toIndex);
    }
  }
  l6(oldCapacity, minCapacity) {
    var newCapacity = oldCapacity + (oldCapacity >> 1) | 0;
    if ((newCapacity - minCapacity | 0) < 0)
      newCapacity = minCapacity;
    if ((newCapacity - 2147483639 | 0) > 0)
      newCapacity = minCapacity > 2147483639 ? 2147483647 : 2147483639;
    return newCapacity;
  }
  s3(c) {
    var hashCode_0 = 1;
    var _iterator__ex2g4s = c.f();
    while (_iterator__ex2g4s.g()) {
      var e = _iterator__ex2g4s.h();
      var tmp = imul_0(31, hashCode_0);
      var tmp1_elvis_lhs = e == null ? null : hashCode(e);
      hashCode_0 = tmp + (tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs) | 0;
    }
    return hashCode_0;
  }
  r3(c, other) {
    if (!(c.i() === other.i()))
      return false;
    var otherIterator = other.f();
    var _iterator__ex2g4s = c.f();
    while (_iterator__ex2g4s.g()) {
      var elem = _iterator__ex2g4s.h();
      var elemOther = otherIterator.h();
      if (!equals(elem, elemOther)) {
        return false;
      }
    }
    return true;
  }
}
class AbstractMap$keys$1$iterator$1 {
  constructor($entryIterator) {
    this.ia_1 = $entryIterator;
  }
  g() {
    return this.ia_1.g();
  }
  h() {
    return this.ia_1.h().c1();
  }
}
class Companion_4 {}
class AbstractSet extends AbstractCollection {
  static la($box) {
    return this.s2($box);
  }
  equals(other) {
    if (other === this)
      return true;
    if (!(!(other == null) ? isInterface(other, KtSet) : false))
      return false;
    return Companion_instance_5.h4(this, other);
  }
  hashCode() {
    return Companion_instance_5.i4(this);
  }
}
class AbstractMap$keys$1 extends AbstractSet {
  constructor(this$0) {
    return new.target.ka(this$0);
  }
  static ka(this$0, $box) {
    if ($box === VOID)
      $box = {};
    $box.ja_1 = this$0;
    return this.la($box);
  }
  i5(element) {
    return this.ja_1.e1(element);
  }
  t(element) {
    if (!true)
      return false;
    return this.i5(element);
  }
  f() {
    var entryIterator = this.ja_1.h1().f();
    return new AbstractMap$keys$1$iterator$1(entryIterator);
  }
  i() {
    return this.ja_1.i();
  }
}
class Companion_5 {
  i4(c) {
    var hashCode_0 = 0;
    var _iterator__ex2g4s = c.f();
    while (_iterator__ex2g4s.g()) {
      var element = _iterator__ex2g4s.h();
      var tmp = hashCode_0;
      var tmp1_elvis_lhs = element == null ? null : hashCode(element);
      hashCode_0 = tmp + (tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs) | 0;
    }
    return hashCode_0;
  }
  h4(c, other) {
    if (!(c.i() === other.i()))
      return false;
    return c.u(other);
  }
}
class EmptyList {
  constructor() {
    this.ma_1 = -7390468764508069838n;
  }
  equals(other) {
    var tmp;
    if (!(other == null) ? isInterface(other, KtList) : false) {
      tmp = other.s();
    } else {
      tmp = false;
    }
    return tmp;
  }
  hashCode() {
    return 1;
  }
  toString() {
    return '[]';
  }
  i() {
    return 0;
  }
  s() {
    return true;
  }
  na(element) {
    return false;
  }
  t(element) {
    if (!false)
      return false;
    var tmp;
    if (false) {
      tmp = element;
    } else {
      tmp = THROW_CCE();
    }
    return this.na(tmp);
  }
  oa(elements) {
    return elements.s();
  }
  u(elements) {
    return this.oa(elements);
  }
  w(index) {
    throw IndexOutOfBoundsException.l8("Empty list doesn't contain element at index " + index + '.');
  }
  pa(element) {
    return -1;
  }
  x(element) {
    if (!false)
      return -1;
    var tmp;
    if (false) {
      tmp = element;
    } else {
      tmp = THROW_CCE();
    }
    return this.pa(tmp);
  }
  qa(element) {
    return -1;
  }
  y(element) {
    if (!false)
      return -1;
    var tmp;
    if (false) {
      tmp = element;
    } else {
      tmp = THROW_CCE();
    }
    return this.qa(tmp);
  }
  f() {
    return EmptyIterator_instance;
  }
  z() {
    return EmptyIterator_instance;
  }
  a1(index) {
    if (!(index === 0))
      throw IndexOutOfBoundsException.l8('Index: ' + index);
    return EmptyIterator_instance;
  }
  b1(fromIndex, toIndex) {
    if (fromIndex === 0 && toIndex === 0)
      return this;
    throw IndexOutOfBoundsException.l8('fromIndex: ' + fromIndex + ', toIndex: ' + toIndex);
  }
}
class EmptyIterator {
  g() {
    return false;
  }
  e3() {
    return false;
  }
  f3() {
    return 0;
  }
  h() {
    throw NoSuchElementException.x2();
  }
  g3() {
    throw NoSuchElementException.x2();
  }
}
class IndexedValue {
  constructor(index, value) {
    this.ra_1 = index;
    this.sa_1 = value;
  }
  toString() {
    return 'IndexedValue(index=' + this.ra_1 + ', value=' + toString_0(this.sa_1) + ')';
  }
  hashCode() {
    var result = this.ra_1;
    result = imul_0(result, 31) + (this.sa_1 == null ? 0 : hashCode(this.sa_1)) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof IndexedValue))
      return false;
    if (!(this.ra_1 === other.ra_1))
      return false;
    if (!equals(this.sa_1, other.sa_1))
      return false;
    return true;
  }
}
class IndexingIterable {
  constructor(iteratorFactory) {
    this.ta_1 = iteratorFactory;
  }
  f() {
    return new IndexingIterator(this.ta_1());
  }
}
class IndexingIterator {
  constructor(iterator) {
    this.ua_1 = iterator;
    this.va_1 = 0;
  }
  g() {
    return this.ua_1.g();
  }
  h() {
    var _unary__edvuaz = this.va_1;
    this.va_1 = _unary__edvuaz + 1 | 0;
    return new IndexedValue(checkIndexOverflow(_unary__edvuaz), this.ua_1.h());
  }
}
class EmptyMap {
  constructor() {
    this.wa_1 = 8246714829545688274n;
  }
  equals(other) {
    var tmp;
    if (!(other == null) ? isInterface(other, KtMap) : false) {
      tmp = other.s();
    } else {
      tmp = false;
    }
    return tmp;
  }
  hashCode() {
    return 0;
  }
  toString() {
    return '{}';
  }
  i() {
    return 0;
  }
  s() {
    return true;
  }
  xa(key) {
    return false;
  }
  e1(key) {
    if (!true)
      return false;
    return this.xa(key);
  }
  ya(key) {
    return null;
  }
  f1(key) {
    if (!true)
      return null;
    return this.ya(key);
  }
  h1() {
    return EmptySet_instance;
  }
  g1() {
    return EmptySet_instance;
  }
}
class IntIterator {
  h() {
    return this.db();
  }
}
class EmptySet {
  constructor() {
    this.eb_1 = 3406603774387020532n;
  }
  equals(other) {
    var tmp;
    if (!(other == null) ? isInterface(other, KtSet) : false) {
      tmp = other.s();
    } else {
      tmp = false;
    }
    return tmp;
  }
  hashCode() {
    return 0;
  }
  toString() {
    return '[]';
  }
  i() {
    return 0;
  }
  s() {
    return true;
  }
  oa(elements) {
    return elements.s();
  }
  u(elements) {
    return this.oa(elements);
  }
  f() {
    return EmptyIterator_instance;
  }
}
class Companion_6 {
  constructor() {
    Companion_instance_6 = this;
    this.r_1 = new IntRange(1, 0);
  }
}
class IntProgression {
  constructor(start, endInclusive, step) {
    if (step === 0)
      throw IllegalArgumentException.q2('Step must be non-zero.');
    if (step === -2147483648)
      throw IllegalArgumentException.q2('Step must be greater than Int.MIN_VALUE to avoid overflow on negation.');
    this.ib_1 = start;
    this.jb_1 = getProgressionLastElement(start, endInclusive, step);
    this.kb_1 = step;
  }
  f() {
    return new IntProgressionIterator(this.ib_1, this.jb_1, this.kb_1);
  }
  s() {
    return this.kb_1 > 0 ? this.ib_1 > this.jb_1 : this.ib_1 < this.jb_1;
  }
  equals(other) {
    var tmp;
    if (other instanceof IntProgression) {
      tmp = this.s() && other.s() || (this.ib_1 === other.ib_1 && this.jb_1 === other.jb_1 && this.kb_1 === other.kb_1);
    } else {
      tmp = false;
    }
    return tmp;
  }
  hashCode() {
    return this.s() ? -1 : imul_0(31, imul_0(31, this.ib_1) + this.jb_1 | 0) + this.kb_1 | 0;
  }
  toString() {
    return this.kb_1 > 0 ? '' + this.ib_1 + '..' + this.jb_1 + ' step ' + this.kb_1 : '' + this.ib_1 + ' downTo ' + this.jb_1 + ' step ' + (-this.kb_1 | 0);
  }
}
class IntRange extends IntProgression {
  constructor(start, endInclusive) {
    Companion_getInstance_6();
    super(start, endInclusive, 1);
  }
  s() {
    return this.ib_1 > this.jb_1;
  }
  equals(other) {
    var tmp;
    if (other instanceof IntRange) {
      tmp = this.s() && other.s() || (this.ib_1 === other.ib_1 && this.jb_1 === other.jb_1);
    } else {
      tmp = false;
    }
    return tmp;
  }
  hashCode() {
    return this.s() ? -1 : imul_0(31, this.ib_1) + this.jb_1 | 0;
  }
  toString() {
    return '' + this.ib_1 + '..' + this.jb_1;
  }
}
class IntProgressionIterator extends IntIterator {
  constructor(first, last, step) {
    super();
    this.lb_1 = step;
    this.mb_1 = last;
    this.nb_1 = this.lb_1 > 0 ? first <= last : first >= last;
    this.ob_1 = this.nb_1 ? first : this.mb_1;
  }
  g() {
    return this.nb_1;
  }
  db() {
    var value = this.ob_1;
    if (value === this.mb_1) {
      if (!this.nb_1)
        throw NoSuchElementException.x2();
      this.nb_1 = false;
    } else {
      this.ob_1 = this.ob_1 + this.lb_1 | 0;
    }
    return value;
  }
}
class Companion_7 {}
class System {
  qb() {
    return systemClockNow();
  }
}
class Companion_8 {
  constructor() {
    Companion_instance_8 = this;
    this.fa_1 = new Instant(-31557014167219200n, 0);
    this.ga_1 = new Instant(31556889864403199n, 999999999);
  }
  ha(epochMilliseconds) {
    // Inline function 'kotlin.floorDiv' call
    var q = divide(epochMilliseconds, 1000n);
    if ((epochMilliseconds ^ 1000n) < 0n && !(multiply(q, 1000n) === epochMilliseconds)) {
      var _unary__edvuaz = q;
      q = subtract(_unary__edvuaz, get_ONE());
    }
    var epochSeconds = q;
    // Inline function 'kotlin.mod' call
    var r = modulo(epochMilliseconds, 1000n);
    // Inline function 'kotlin.Long.times' call
    var this_0 = add(r, 1000n & shiftRight((r ^ 1000n) & (r | negate(r)), 63));
    var tmp$ret$2 = multiply(this_0, fromInt_0(1000000));
    var nanosecondsOfSecond = convertToInt(tmp$ret$2);
    return epochSeconds < -31557014167219200n ? this.fa_1 : epochSeconds > 31556889864403199n ? this.ga_1 : this.rb(epochSeconds, nanosecondsOfSecond);
  }
  sb(epochSeconds, nanosecondAdjustment) {
    // Inline function 'kotlin.floorDiv' call
    var other = 1000000000n;
    var q = divide(nanosecondAdjustment, other);
    if ((nanosecondAdjustment ^ other) < 0n && !(multiply(q, other) === nanosecondAdjustment)) {
      var _unary__edvuaz = q;
      q = subtract(_unary__edvuaz, get_ONE());
    }
    // Inline function 'kotlin.time.safeAddOrElse' call
    var b = q;
    var sum = add(epochSeconds, b);
    if ((epochSeconds ^ sum) < 0n && (epochSeconds ^ b) >= 0n) {
      return epochSeconds > 0n ? Companion_getInstance_8().ga_1 : Companion_getInstance_8().fa_1;
    }
    var seconds = sum;
    var tmp;
    if (seconds < -31557014167219200n) {
      tmp = this.fa_1;
    } else if (seconds > 31556889864403199n) {
      tmp = this.ga_1;
    } else {
      // Inline function 'kotlin.mod' call
      var other_0 = 1000000000n;
      var r = modulo(nanosecondAdjustment, other_0);
      var tmp$ret$3 = add(r, other_0 & shiftRight((r ^ other_0) & (r | negate(r)), 63));
      var nanoseconds = convertToInt(tmp$ret$3);
      tmp = new Instant(seconds, nanoseconds);
    }
    return tmp;
  }
  rb(epochSeconds, nanosecondAdjustment) {
    return this.sb(epochSeconds, fromInt_0(nanosecondAdjustment));
  }
}
class Instant {
  constructor(epochSeconds, nanosecondsOfSecond) {
    Companion_getInstance_8();
    this.tb_1 = epochSeconds;
    this.ub_1 = nanosecondsOfSecond;
    var containsArg = this.tb_1;
    // Inline function 'kotlin.require' call
    if (!(-31557014167219200n <= containsArg ? containsArg <= 31556889864403199n : false)) {
      var message = 'Instant exceeds minimum or maximum instant';
      throw IllegalArgumentException.q2(toString_1(message));
    }
  }
  vb(other) {
    var s = compareTo(this.tb_1, other.tb_1);
    if (!(s === 0)) {
      return s;
    }
    return compareTo(this.ub_1, other.ub_1);
  }
  n1(other) {
    return this.vb(other instanceof Instant ? other : THROW_CCE());
  }
  equals(other) {
    var tmp;
    if (this === other) {
      tmp = true;
    } else {
      var tmp_0;
      var tmp_1;
      if (other instanceof Instant) {
        tmp_1 = this.tb_1 === other.tb_1;
      } else {
        tmp_1 = false;
      }
      if (tmp_1) {
        tmp_0 = this.ub_1 === other.ub_1;
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    }
    return tmp;
  }
  hashCode() {
    return getBigIntHashCode(this.tb_1) + imul_0(51, this.ub_1) | 0;
  }
  toString() {
    return formatIso(this);
  }
}
class Companion_9 {
  wb(instant) {
    var localSecond = instant.tb_1;
    // Inline function 'kotlin.floorDiv' call
    var q = divide(localSecond, 86400n);
    if ((localSecond ^ 86400n) < 0n && !(multiply(q, 86400n) === localSecond)) {
      var _unary__edvuaz = q;
      q = subtract(_unary__edvuaz, get_ONE());
    }
    var epochDays = q;
    // Inline function 'kotlin.mod' call
    var r = modulo(localSecond, 86400n);
    var tmp$ret$1 = add(r, 86400n & shiftRight((r ^ 86400n) & (r | negate(r)), 63));
    var secsOfDay = convertToInt(tmp$ret$1);
    var year;
    var month;
    var day;
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.Long.plus' call
    var zeroDay = add(epochDays, fromInt_0(719528));
    // Inline function 'kotlin.Long.minus' call
    var this_0 = zeroDay;
    zeroDay = subtract(this_0, fromInt_0(60));
    var adjust = 0n;
    if (zeroDay < 0n) {
      // Inline function 'kotlin.Long.plus' call
      var this_1 = zeroDay;
      // Inline function 'kotlin.Long.div' call
      var this_2 = add(this_1, fromInt_0(1));
      // Inline function 'kotlin.Long.minus' call
      var this_3 = divide(this_2, fromInt_0(146097));
      var adjustCycles = subtract(this_3, fromInt_0(1));
      // Inline function 'kotlin.Long.times' call
      adjust = multiply(adjustCycles, fromInt_0(400));
      var tmp = zeroDay;
      // Inline function 'kotlin.Long.times' call
      var this_4 = negate(adjustCycles);
      var tmp$ret$10 = multiply(this_4, fromInt_0(146097));
      zeroDay = add(tmp, tmp$ret$10);
    }
    // Inline function 'kotlin.Long.plus' call
    var this_5 = multiply(numberToLong(400), zeroDay);
    // Inline function 'kotlin.Long.div' call
    var this_6 = add(this_5, fromInt_0(591));
    var yearEst = divide(this_6, fromInt_0(146097));
    var tmp_0 = zeroDay;
    var tmp_1 = multiply(numberToLong(365), yearEst);
    // Inline function 'kotlin.Long.div' call
    var this_7 = yearEst;
    var tmp$ret$13 = divide(this_7, fromInt_0(4));
    var tmp_2 = add(tmp_1, tmp$ret$13);
    // Inline function 'kotlin.Long.div' call
    var this_8 = yearEst;
    var tmp$ret$14 = divide(this_8, fromInt_0(100));
    var tmp_3 = subtract(tmp_2, tmp$ret$14);
    // Inline function 'kotlin.Long.div' call
    var this_9 = yearEst;
    var tmp$ret$15 = divide(this_9, fromInt_0(400));
    var doyEst = subtract(tmp_0, add(tmp_3, tmp$ret$15));
    if (doyEst < 0n) {
      var _unary__edvuaz_0 = yearEst;
      yearEst = subtract(_unary__edvuaz_0, get_ONE());
      var tmp_4 = zeroDay;
      var tmp_5 = multiply(numberToLong(365), yearEst);
      // Inline function 'kotlin.Long.div' call
      var this_10 = yearEst;
      var tmp$ret$16 = divide(this_10, fromInt_0(4));
      var tmp_6 = add(tmp_5, tmp$ret$16);
      // Inline function 'kotlin.Long.div' call
      var this_11 = yearEst;
      var tmp$ret$17 = divide(this_11, fromInt_0(100));
      var tmp_7 = subtract(tmp_6, tmp$ret$17);
      // Inline function 'kotlin.Long.div' call
      var this_12 = yearEst;
      var tmp$ret$18 = divide(this_12, fromInt_0(400));
      doyEst = subtract(tmp_4, add(tmp_7, tmp$ret$18));
    }
    yearEst = add(yearEst, adjust);
    var marchDoy0 = convertToInt(doyEst);
    var marchMonth0 = (imul_0(marchDoy0, 5) + 2 | 0) / 153 | 0;
    month = ((marchMonth0 + 2 | 0) % 12 | 0) + 1 | 0;
    day = (marchDoy0 - ((imul_0(marchMonth0, 306) + 5 | 0) / 10 | 0) | 0) + 1 | 0;
    var tmp0 = yearEst;
    // Inline function 'kotlin.Long.plus' call
    var other = marchMonth0 / 10 | 0;
    var tmp$ret$19 = add(tmp0, fromInt_0(other));
    year = convertToInt(tmp$ret$19);
    var hours = secsOfDay / 3600 | 0;
    var secondWithoutHours = secsOfDay - imul_0(hours, 3600) | 0;
    var minutes = secondWithoutHours / 60 | 0;
    var second = secondWithoutHours - imul_0(minutes, 60) | 0;
    return new UnboundLocalDateTime(year, month, day, hours, minutes, second, instant.ub_1);
  }
}
class UnboundLocalDateTime {
  constructor(year, month, day, hour, minute, second, nanosecond) {
    this.xb_1 = year;
    this.yb_1 = month;
    this.zb_1 = day;
    this.ac_1 = hour;
    this.bc_1 = minute;
    this.cc_1 = second;
    this.dc_1 = nanosecond;
  }
  toString() {
    return 'UnboundLocalDateTime(' + this.xb_1 + '-' + this.yb_1 + '-' + this.zb_1 + ' ' + this.ac_1 + ':' + this.bc_1 + ':' + this.cc_1 + '.' + this.dc_1 + ')';
  }
}
class LazyThreadSafetyMode extends Enum {}
class UnsafeLazyImpl {
  constructor(initializer) {
    this.ec_1 = initializer;
    this.fc_1 = UNINITIALIZED_VALUE_instance;
  }
  d1() {
    if (this.fc_1 === UNINITIALIZED_VALUE_instance) {
      this.fc_1 = ensureNotNull(this.ec_1)();
      this.ec_1 = null;
    }
    return this.fc_1;
  }
  gc() {
    return !(this.fc_1 === UNINITIALIZED_VALUE_instance);
  }
  toString() {
    return this.gc() ? toString_0(this.d1()) : 'Lazy value not initialized yet.';
  }
}
class UNINITIALIZED_VALUE {}
class Pair {
  constructor(first, second) {
    this.za_1 = first;
    this.ab_1 = second;
  }
  toString() {
    return '(' + toString_0(this.za_1) + ', ' + toString_0(this.ab_1) + ')';
  }
  bb() {
    return this.za_1;
  }
  cb() {
    return this.ab_1;
  }
  hashCode() {
    var result = this.za_1 == null ? 0 : hashCode(this.za_1);
    result = imul_0(result, 31) + (this.ab_1 == null ? 0 : hashCode(this.ab_1)) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof Pair))
      return false;
    if (!equals(this.za_1, other.za_1))
      return false;
    if (!equals(this.ab_1, other.ab_1))
      return false;
    return true;
  }
}
class Companion_10 {
  constructor() {
    Companion_instance_10 = this;
    this.hc_1 = new Uuid(0n, 0n);
    this.ic_1 = 16;
    this.jc_1 = 128;
  }
  kc(mostSignificantBits, leastSignificantBits) {
    var tmp;
    if (mostSignificantBits === 0n && leastSignificantBits === 0n) {
      tmp = this.hc_1;
    } else {
      tmp = new Uuid(mostSignificantBits, leastSignificantBits);
    }
    return tmp;
  }
  lc(byteArray) {
    // Inline function 'kotlin.require' call
    if (!(byteArray.length === 16)) {
      var message = 'Expected exactly 16 bytes, but was ' + truncateForErrorMessage(byteArray, 32) + ' of size ' + byteArray.length;
      throw IllegalArgumentException.q2(toString_1(message));
    }
    return this.kc(getLongAt(byteArray, 0), getLongAt(byteArray, 8));
  }
  mc() {
    return this.nc();
  }
  nc() {
    return secureRandomUuid();
  }
}
class Uuid {
  constructor(mostSignificantBits, leastSignificantBits) {
    Companion_getInstance_10();
    this.oc_1 = mostSignificantBits;
    this.pc_1 = leastSignificantBits;
  }
  toString() {
    return this.qc();
  }
  qc() {
    var bytes = new Int8Array(36);
    formatBytesInto(this.oc_1, bytes, 0, 0, 4);
    // Inline function 'kotlin.code' call
    var this_0 = _Char___init__impl__6a9atx(45);
    var tmp$ret$0 = Char__toInt_impl_vasixd(this_0);
    bytes[8] = toByte(tmp$ret$0);
    formatBytesInto(this.oc_1, bytes, 9, 4, 6);
    // Inline function 'kotlin.code' call
    var this_1 = _Char___init__impl__6a9atx(45);
    var tmp$ret$1 = Char__toInt_impl_vasixd(this_1);
    bytes[13] = toByte(tmp$ret$1);
    formatBytesInto(this.oc_1, bytes, 14, 6, 8);
    // Inline function 'kotlin.code' call
    var this_2 = _Char___init__impl__6a9atx(45);
    var tmp$ret$2 = Char__toInt_impl_vasixd(this_2);
    bytes[18] = toByte(tmp$ret$2);
    formatBytesInto(this.pc_1, bytes, 19, 0, 2);
    // Inline function 'kotlin.code' call
    var this_3 = _Char___init__impl__6a9atx(45);
    var tmp$ret$3 = Char__toInt_impl_vasixd(this_3);
    bytes[23] = toByte(tmp$ret$3);
    formatBytesInto(this.pc_1, bytes, 24, 2, 8);
    return decodeToString(bytes);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof Uuid))
      return false;
    return this.oc_1 === other.oc_1 && this.pc_1 === other.pc_1;
  }
  rc(other) {
    var tmp;
    if (!(this.oc_1 === other.oc_1)) {
      // Inline function 'kotlin.toULong' call
      var this_0 = this.oc_1;
      var tmp0 = _ULong___init__impl__c78o9k(this_0);
      // Inline function 'kotlin.toULong' call
      var this_1 = other.oc_1;
      // Inline function 'kotlin.ULong.compareTo' call
      var other_0 = _ULong___init__impl__c78o9k(this_1);
      tmp = ulongCompare(_ULong___get_data__impl__fggpzb(tmp0), _ULong___get_data__impl__fggpzb(other_0));
    } else {
      // Inline function 'kotlin.toULong' call
      var this_2 = this.pc_1;
      var tmp0_0 = _ULong___init__impl__c78o9k(this_2);
      // Inline function 'kotlin.toULong' call
      var this_3 = other.pc_1;
      // Inline function 'kotlin.ULong.compareTo' call
      var other_1 = _ULong___init__impl__c78o9k(this_3);
      tmp = ulongCompare(_ULong___get_data__impl__fggpzb(tmp0_0), _ULong___get_data__impl__fggpzb(other_1));
    }
    return tmp;
  }
  n1(other) {
    return this.rc(other instanceof Uuid ? other : THROW_CCE());
  }
  hashCode() {
    return getBigIntHashCode(this.oc_1 ^ this.pc_1);
  }
}
class KSerializer {}
class AbstractPolymorphicSerializer {
  static gd() {
    return createThis(this);
  }
}
class PolymorphicSerializer extends AbstractPolymorphicSerializer {
  constructor(baseClass) {
    return new.target.fd(baseClass);
  }
  static fd(baseClass) {
    var $this = this.gd();
    $this.cd_1 = baseClass;
    $this.dd_1 = emptyList();
    var tmp = $this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    tmp.ed_1 = lazy(tmp_0, PolymorphicSerializer$descriptor$delegate$lambda($this));
    return $this;
  }
  static hd(baseClass, classAnnotations) {
    var $this = this.fd(baseClass);
    $this.dd_1 = asList(classAnnotations);
    return $this;
  }
  sc() {
    var tmp0 = this.ed_1;
    var tmp = KProperty1;
    // Inline function 'kotlin.getValue' call
    getPropertyCallableRef('descriptor', 1, tmp, PolymorphicSerializer$_get_descriptor_$ref_8tw9if(), null);
    return tmp0.d1();
  }
  toString() {
    return 'kotlinx.serialization.PolymorphicSerializer(baseClass: ' + toString_1(this.cd_1) + ')';
  }
}
class SerializationException extends IllegalArgumentException {
  static wd() {
    var $this = this.a8();
    init_kotlinx_serialization_SerializationException($this);
    return $this;
  }
  static ud(message, cause) {
    var $this = this.b8(message, cause);
    init_kotlinx_serialization_SerializationException($this);
    return $this;
  }
}
class MissingFieldException extends SerializationException {
  constructor(message, cause, missingFields, serialName) {
    return new.target.pd(message, cause, missingFields, serialName);
  }
  static pd(message, cause, missingFields, serialName) {
    var $this = this.ud(message, cause);
    captureStack($this, $this.od_1);
    $this.md_1 = missingFields;
    $this.nd_1 = serialName;
    return $this;
  }
  static vd(missingFields, serialName) {
    var tmp0_message = missingFields.i() === 1 ? "Field '" + missingFields.w(0) + "' is required for type with serial name '" + serialName + "', but it was missing" : 'Fields ' + toString_1(missingFields) + " are required for type with serial name '" + serialName + "', but they were missing";
    return this.pd(tmp0_message, null, missingFields, serialName);
  }
}
class SerialDescriptor {}
function get_isNullable() {
  return false;
}
class ContextDescriptor {
  constructor(original, kClass) {
    this.yd_1 = original;
    this.zd_1 = kClass;
    this.ae_1 = this.yd_1.be() + '<' + this.zd_1.s8() + '>';
  }
  be() {
    return this.ae_1;
  }
  equals(other) {
    var tmp0_elvis_lhs = other instanceof ContextDescriptor ? other : null;
    var tmp;
    if (tmp0_elvis_lhs == null) {
      return false;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    var another = tmp;
    return equals(this.yd_1, another.yd_1) && another.zd_1.equals(this.zd_1);
  }
  hashCode() {
    var result = this.zd_1.hashCode();
    result = imul_0(31, result) + getStringHashCode(this.ae_1) | 0;
    return result;
  }
  toString() {
    return 'ContextDescriptor(kClass: ' + toString_1(this.zd_1) + ', original: ' + toString_1(this.yd_1) + ')';
  }
  ce() {
    return this.yd_1.ce();
  }
  xd() {
    return this.yd_1.xd();
  }
  de() {
    return this.yd_1.de();
  }
  ee(index) {
    return this.yd_1.ee(index);
  }
  fe(index) {
    return this.yd_1.fe(index);
  }
}
class elementDescriptors$1 {
  constructor($this_elementDescriptors) {
    this.he_1 = $this_elementDescriptors;
    this.ge_1 = $this_elementDescriptors.de();
  }
  g() {
    return this.ge_1 > 0;
  }
  h() {
    var tmp = this.he_1.de();
    var _unary__edvuaz = this.ge_1;
    this.ge_1 = _unary__edvuaz - 1 | 0;
    return this.he_1.fe(tmp - _unary__edvuaz | 0);
  }
}
class elementDescriptors$$inlined$Iterable$1 {
  constructor($this_elementDescriptors) {
    this.ie_1 = $this_elementDescriptors;
  }
  f() {
    return new elementDescriptors$1(this.ie_1);
  }
}
class ClassSerialDescriptorBuilder {
  constructor(serialName) {
    this.tc_1 = serialName;
    this.uc_1 = false;
    this.vc_1 = emptyList();
    this.wc_1 = ArrayList.k4();
    this.xc_1 = HashSet.z5();
    this.yc_1 = ArrayList.k4();
    this.zc_1 = ArrayList.k4();
    this.ad_1 = ArrayList.k4();
  }
  je(elementName, descriptor, annotations, isOptional) {
    // Inline function 'kotlin.require' call
    if (!this.xc_1.o(elementName)) {
      var message = "Element with name '" + elementName + "' is already registered in " + this.tc_1;
      throw IllegalArgumentException.q2(toString_1(message));
    }
    // Inline function 'kotlin.collections.plusAssign' call
    this.wc_1.o(elementName);
    // Inline function 'kotlin.collections.plusAssign' call
    this.yc_1.o(descriptor);
    // Inline function 'kotlin.collections.plusAssign' call
    this.zc_1.o(annotations);
    // Inline function 'kotlin.collections.plusAssign' call
    this.ad_1.o(isOptional);
  }
  bd(elementName, descriptor, annotations, isOptional, $super) {
    annotations = annotations === VOID ? emptyList() : annotations;
    isOptional = isOptional === VOID ? false : isOptional;
    var tmp;
    if ($super === VOID) {
      this.je(elementName, descriptor, annotations, isOptional);
      tmp = Unit_instance;
    } else {
      tmp = $super.je.call(this, elementName, descriptor, annotations, isOptional);
    }
    return tmp;
  }
}
class CachedNames {}
class SerialDescriptorImpl {
  constructor(serialName, kind, elementsCount, typeParameters, builder) {
    this.ke_1 = serialName;
    this.le_1 = kind;
    this.me_1 = elementsCount;
    this.ne_1 = builder.vc_1;
    this.oe_1 = toHashSet(builder.wc_1);
    var tmp = this;
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = builder.wc_1;
    tmp.pe_1 = copyToArray(this_0);
    this.qe_1 = compactArray(builder.yc_1);
    var tmp_0 = this;
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_1 = builder.zc_1;
    tmp_0.re_1 = copyToArray(this_1);
    this.se_1 = toBooleanArray(builder.ad_1);
    var tmp_1 = this;
    // Inline function 'kotlin.collections.map' call
    var this_2 = withIndex(this.pe_1);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList.m(collectionSizeOrDefault(this_2, 10));
    var _iterator__ex2g4s = this_2.f();
    while (_iterator__ex2g4s.g()) {
      var item = _iterator__ex2g4s.h();
      var tmp$ret$4 = to(item.sa_1, item.ra_1);
      destination.o(tmp$ret$4);
    }
    tmp_1.te_1 = toMap(destination);
    this.ue_1 = compactArray(typeParameters);
    var tmp_2 = this;
    tmp_2.ve_1 = lazy_0(SerialDescriptorImpl$_hashCode$delegate$lambda(this));
  }
  be() {
    return this.ke_1;
  }
  ce() {
    return this.le_1;
  }
  de() {
    return this.me_1;
  }
  we() {
    return this.oe_1;
  }
  ee(index) {
    return getChecked(this.pe_1, index);
  }
  fe(index) {
    return getChecked(this.qe_1, index);
  }
  equals(other) {
    var tmp$ret$0;
    $l$block_5: {
      // Inline function 'kotlinx.serialization.internal.equalsImpl' call
      if (this === other) {
        tmp$ret$0 = true;
        break $l$block_5;
      }
      if (!(other instanceof SerialDescriptorImpl)) {
        tmp$ret$0 = false;
        break $l$block_5;
      }
      if (!(this.be() === other.be())) {
        tmp$ret$0 = false;
        break $l$block_5;
      }
      if (!contentEquals(this.ue_1, other.ue_1)) {
        tmp$ret$0 = false;
        break $l$block_5;
      }
      if (!(this.de() === other.de())) {
        tmp$ret$0 = false;
        break $l$block_5;
      }
      var inductionVariable = 0;
      var last = this.de();
      if (inductionVariable < last)
        do {
          var index = inductionVariable;
          inductionVariable = inductionVariable + 1 | 0;
          if (!(this.fe(index).be() === other.fe(index).be())) {
            tmp$ret$0 = false;
            break $l$block_5;
          }
          if (!equals(this.fe(index).ce(), other.fe(index).ce())) {
            tmp$ret$0 = false;
            break $l$block_5;
          }
        }
         while (inductionVariable < last);
      tmp$ret$0 = true;
    }
    return tmp$ret$0;
  }
  hashCode() {
    return _get__hashCode__tgwhef(this);
  }
  toString() {
    return toStringImpl(this);
  }
}
class SerialKind {
  toString() {
    return ensureNotNull(getKClassFromExpression(this).s8());
  }
  hashCode() {
    return getStringHashCode(this.toString());
  }
}
class PrimitiveKind extends SerialKind {}
class INT extends PrimitiveKind {
  constructor() {
    INT_instance = null;
    super();
    INT_instance = this;
  }
}
class STRING extends PrimitiveKind {
  constructor() {
    STRING_instance = null;
    super();
    STRING_instance = this;
  }
}
class PolymorphicKind extends SerialKind {}
class OPEN extends PolymorphicKind {
  constructor() {
    OPEN_instance = null;
    super();
    OPEN_instance = this;
  }
}
class CONTEXTUAL extends SerialKind {
  constructor() {
    CONTEXTUAL_instance = null;
    super();
    CONTEXTUAL_instance = this;
  }
}
class StructureKind extends SerialKind {}
class CLASS extends StructureKind {
  constructor() {
    CLASS_instance = null;
    super();
    CLASS_instance = this;
  }
}
class NullableSerializer {
  constructor(serializer) {
    this.xe_1 = serializer;
    this.ye_1 = new SerialDescriptorForNullable(this.xe_1.sc());
  }
  sc() {
    return this.ye_1;
  }
  equals(other) {
    if (this === other)
      return true;
    if (other == null || !getKClassFromExpression(this).equals(getKClassFromExpression(other)))
      return false;
    if (!(other instanceof NullableSerializer))
      THROW_CCE();
    if (!equals(this.xe_1, other.xe_1))
      return false;
    return true;
  }
  hashCode() {
    return hashCode(this.xe_1);
  }
}
class SerialDescriptorForNullable {
  constructor(original) {
    this.ze_1 = original;
    this.af_1 = this.ze_1.be() + '?';
    this.bf_1 = cachedSerialNames(this.ze_1);
  }
  be() {
    return this.af_1;
  }
  we() {
    return this.bf_1;
  }
  xd() {
    return true;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof SerialDescriptorForNullable))
      return false;
    if (!equals(this.ze_1, other.ze_1))
      return false;
    return true;
  }
  toString() {
    return toString_1(this.ze_1) + '?';
  }
  hashCode() {
    return imul_0(hashCode(this.ze_1), 31);
  }
  ce() {
    return this.ze_1.ce();
  }
  de() {
    return this.ze_1.de();
  }
  ee(index) {
    return this.ze_1.ee(index);
  }
  fe(index) {
    return this.ze_1.fe(index);
  }
}
class PluginGeneratedSerialDescriptor {
  constructor(serialName, generatedSerializer, elementsCount) {
    generatedSerializer = generatedSerializer === VOID ? null : generatedSerializer;
    this.cf_1 = serialName;
    this.df_1 = generatedSerializer;
    this.ef_1 = elementsCount;
    this.ff_1 = -1;
    var tmp = this;
    var tmp_0 = 0;
    var tmp_1 = this.ef_1;
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp_2 = Array(tmp_1);
    while (tmp_0 < tmp_1) {
      tmp_2[tmp_0] = '[UNINITIALIZED]';
      tmp_0 = tmp_0 + 1 | 0;
    }
    tmp.gf_1 = tmp_2;
    var tmp_3 = this;
    // Inline function 'kotlin.arrayOfNulls' call
    var size = this.ef_1;
    tmp_3.hf_1 = Array(size);
    this.if_1 = null;
    this.jf_1 = booleanArray(this.ef_1);
    this.kf_1 = emptyMap();
    var tmp_4 = this;
    var tmp_5 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    tmp_4.lf_1 = lazy(tmp_5, PluginGeneratedSerialDescriptor$childSerializers$delegate$lambda(this));
    var tmp_6 = this;
    var tmp_7 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    tmp_6.mf_1 = lazy(tmp_7, PluginGeneratedSerialDescriptor$typeParameterDescriptors$delegate$lambda(this));
    var tmp_8 = this;
    var tmp_9 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    tmp_8.nf_1 = lazy(tmp_9, PluginGeneratedSerialDescriptor$_hashCode$delegate$lambda(this));
  }
  be() {
    return this.cf_1;
  }
  de() {
    return this.ef_1;
  }
  ce() {
    return CLASS_getInstance();
  }
  we() {
    return this.kf_1.g1();
  }
  qf() {
    var tmp0 = this.mf_1;
    var tmp = KProperty1;
    // Inline function 'kotlin.getValue' call
    getPropertyCallableRef('typeParameterDescriptors', 1, tmp, PluginGeneratedSerialDescriptor$_get_typeParameterDescriptors_$ref_jk3pka(), null);
    return tmp0.d1();
  }
  rf(name, isOptional) {
    this.ff_1 = this.ff_1 + 1 | 0;
    this.gf_1[this.ff_1] = name;
    this.jf_1[this.ff_1] = isOptional;
    this.hf_1[this.ff_1] = null;
    if (this.ff_1 === (this.ef_1 - 1 | 0)) {
      this.kf_1 = buildIndices(this);
    }
  }
  fe(index) {
    return getChecked(_get_childSerializers__7vnyfa(this), index).sc();
  }
  ee(index) {
    return getChecked(this.gf_1, index);
  }
  equals(other) {
    var tmp$ret$0;
    $l$block_5: {
      // Inline function 'kotlinx.serialization.internal.equalsImpl' call
      if (this === other) {
        tmp$ret$0 = true;
        break $l$block_5;
      }
      if (!(other instanceof PluginGeneratedSerialDescriptor)) {
        tmp$ret$0 = false;
        break $l$block_5;
      }
      if (!(this.be() === other.be())) {
        tmp$ret$0 = false;
        break $l$block_5;
      }
      if (!contentEquals(this.qf(), other.qf())) {
        tmp$ret$0 = false;
        break $l$block_5;
      }
      if (!(this.de() === other.de())) {
        tmp$ret$0 = false;
        break $l$block_5;
      }
      var inductionVariable = 0;
      var last = this.de();
      if (inductionVariable < last)
        do {
          var index = inductionVariable;
          inductionVariable = inductionVariable + 1 | 0;
          if (!(this.fe(index).be() === other.fe(index).be())) {
            tmp$ret$0 = false;
            break $l$block_5;
          }
          if (!equals(this.fe(index).ce(), other.fe(index).ce())) {
            tmp$ret$0 = false;
            break $l$block_5;
          }
        }
         while (inductionVariable < last);
      tmp$ret$0 = true;
    }
    return tmp$ret$0;
  }
  hashCode() {
    return _get__hashCode__tgwhef_0(this);
  }
  toString() {
    return toStringImpl(this);
  }
}
class GeneratedSerializer {}
function typeParametersSerializers() {
  return get_EMPTY_SERIALIZER_ARRAY();
}
class StringSerializer {
  constructor() {
    StringSerializer_instance = this;
    this.sf_1 = new PrimitiveSerialDescriptor('kotlin.String', STRING_getInstance());
  }
  sc() {
    return this.sf_1;
  }
}
class IntSerializer {
  constructor() {
    IntSerializer_instance = this;
    this.tf_1 = new PrimitiveSerialDescriptor('kotlin.Int', INT_getInstance());
  }
  sc() {
    return this.tf_1;
  }
}
class PrimitiveSerialDescriptor {
  constructor(serialName, kind) {
    this.uf_1 = serialName;
    this.vf_1 = kind;
  }
  be() {
    return this.uf_1;
  }
  ce() {
    return this.vf_1;
  }
  de() {
    return 0;
  }
  ee(index) {
    error(this);
  }
  fe(index) {
    error(this);
  }
  toString() {
    return 'PrimitiveDescriptor(' + this.uf_1 + ')';
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof PrimitiveSerialDescriptor))
      return false;
    if (this.uf_1 === other.uf_1 && equals(this.vf_1, other.vf_1))
      return true;
    return false;
  }
  hashCode() {
    return getStringHashCode(this.uf_1) + imul_0(31, this.vf_1.hashCode()) | 0;
  }
}
class SerializableWith {}
class Message {}
class Command {}
class Event {}
class Problem {}
class Query {}
class WithEnvelopeId {}
class WithEnvelopeData {}
class EnvelopeDTO {}
class Companion_11 {
  constructor() {
    Companion_instance_11 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.dsl.cqrs.envelope.Envelope', null, 7);
    tmp0_serialDesc.rf('id', false);
    tmp0_serialDesc.rf('data', false);
    tmp0_serialDesc.rf('type', false);
    tmp0_serialDesc.rf('datacontenttype', true);
    tmp0_serialDesc.rf('specversion', true);
    tmp0_serialDesc.rf('source', true);
    tmp0_serialDesc.rf('time', true);
    this.dg_1 = tmp0_serialDesc;
  }
}
class Envelope {
  constructor(id, data, type, datacontenttype, specversion, source, time) {
    Companion_getInstance_11();
    datacontenttype = datacontenttype === VOID ? null : datacontenttype;
    specversion = specversion === VOID ? null : specversion;
    source = source === VOID ? null : source;
    time = time === VOID ? null : time;
    this.eg_1 = id;
    this.fg_1 = data;
    this.gg_1 = type;
    this.hg_1 = datacontenttype;
    this.ig_1 = specversion;
    this.jg_1 = source;
    this.kg_1 = time;
  }
  wf() {
    return this.eg_1;
  }
  xf() {
    return this.fg_1;
  }
  yf() {
    return this.gg_1;
  }
  zf() {
    return this.hg_1;
  }
  ag() {
    return this.ig_1;
  }
  bg() {
    return this.jg_1;
  }
  cg() {
    return this.kg_1;
  }
  static lg(seen0, id, data, type, datacontenttype, specversion, source, time, serializationConstructorMarker) {
    Companion_getInstance_11();
    if (!(7 === (7 & seen0))) {
      throwMissingFieldException(seen0, 7, Companion_getInstance_11().dg_1);
    }
    var $this = createThis(this);
    $this.eg_1 = id;
    $this.fg_1 = data;
    $this.gg_1 = type;
    if (0 === (seen0 & 8))
      $this.hg_1 = null;
    else
      $this.hg_1 = datacontenttype;
    if (0 === (seen0 & 16))
      $this.ig_1 = null;
    else
      $this.ig_1 = specversion;
    if (0 === (seen0 & 32))
      $this.jg_1 = null;
    else
      $this.jg_1 = source;
    if (0 === (seen0 & 64))
      $this.kg_1 = null;
    else
      $this.kg_1 = time;
    return $this;
  }
  get id() {
    return this.wf();
  }
  get data() {
    return this.xf();
  }
  get type() {
    return this.yf();
  }
  get datacontenttype() {
    return this.zf();
  }
  get specversion() {
    return this.ag();
  }
  get source() {
    return this.bg();
  }
  get time() {
    return this.cg();
  }
}
class F2ErrorDTO {}
class Companion_12 {}
class $serializer {
  constructor() {
    $serializer_instance = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.dsl.cqrs.error.F2Error', this, 5);
    tmp0_serialDesc.rf('message', false);
    tmp0_serialDesc.rf('id', true);
    tmp0_serialDesc.rf('timestamp', true);
    tmp0_serialDesc.rf('code', true);
    tmp0_serialDesc.rf('requestId', true);
    this.pg_1 = tmp0_serialDesc;
  }
  sc() {
    return this.pg_1;
  }
  of() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), get_nullable(StringSerializer_getInstance()), StringSerializer_getInstance(), IntSerializer_getInstance(), get_nullable(StringSerializer_getInstance())];
  }
}
class F2Error {
  constructor(message, id, timestamp, code, requestId) {
    id = id === VOID ? Companion_getInstance_10().mc().toString() : id;
    timestamp = timestamp === VOID ? System_instance.qb().toString() : timestamp;
    code = code === VOID ? 500 : code;
    requestId = requestId === VOID ? null : requestId;
    this.qg_1 = message;
    this.rg_1 = id;
    this.sg_1 = timestamp;
    this.tg_1 = code;
    this.ug_1 = requestId;
  }
  y7() {
    return this.qg_1;
  }
  wf() {
    return this.rg_1;
  }
  mg() {
    return this.sg_1;
  }
  ng() {
    return this.tg_1;
  }
  og() {
    return this.ug_1;
  }
  toString() {
    return "F2Error(timestamp='" + this.timestamp + "', code=" + this.code + ", requestId='" + this.requestId + "', message='" + this.message + "')";
  }
  static vg(seen0, message, id, timestamp, code, requestId, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance().pg_1);
    }
    var $this = createThis(this);
    $this.qg_1 = message;
    if (0 === (seen0 & 2))
      $this.rg_1 = Companion_getInstance_10().mc().toString();
    else
      $this.rg_1 = id;
    if (0 === (seen0 & 4))
      $this.sg_1 = System_instance.qb().toString();
    else
      $this.sg_1 = timestamp;
    if (0 === (seen0 & 8))
      $this.tg_1 = 500;
    else
      $this.tg_1 = code;
    if (0 === (seen0 & 16))
      $this.ug_1 = null;
    else
      $this.ug_1 = requestId;
    return $this;
  }
  get message() {
    return this.y7();
  }
  get id() {
    return this.wf();
  }
  get timestamp() {
    return this.mg();
  }
  get code() {
    return this.ng();
  }
  get requestId() {
    return this.og();
  }
}
class Companion_13 {
  wg(message, id, requestId, code, cause) {
    var tmp0_timestamp = System_instance.qb().toString();
    return F2Exception.ah(new F2Error(message, id, tmp0_timestamp, code, requestId), cause);
  }
  invoke(message, id, requestId, code, cause, $super) {
    id = id === VOID ? '' : id;
    requestId = requestId === VOID ? '' : requestId;
    code = code === VOID ? 500 : code;
    cause = cause === VOID ? null : cause;
    return $super === VOID ? this.wg(message, id, requestId, code, cause) : $super.wg.call(this, message, id, requestId, code, cause);
  }
}
class F2Exception extends RuntimeException {
  constructor(error, cause) {
    return new.target.ah(error, cause);
  }
  static ah(error, cause) {
    cause = cause === VOID ? null : cause;
    var $this = this.c8(error.message, cause);
    captureStack($this, $this.zg_1);
    $this.error = error;
    return $this;
  }
  bh() {
    return this.error;
  }
}
class Match {}
function and(match) {
  return new AndMatch(listOf_0([this, match]));
}
function or(match) {
  return new OrMatch(listOf_0([this, match]));
}
class AndMatch {
  constructor(matches, negative) {
    negative = negative === VOID ? false : negative;
    this.dh_1 = matches;
    this.eh_1 = negative;
  }
  ch() {
    return this.eh_1;
  }
  map(transform) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = this.dh_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList.m(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.f();
    while (_iterator__ex2g4s.g()) {
      var item = _iterator__ex2g4s.h();
      var tmp$ret$2 = item.map(transform);
      destination.o(tmp$ret$2);
    }
    return new AndMatch(destination, this.eh_1);
  }
  not() {
    return this.fh(VOID, !this.eh_1);
  }
  and(match) {
    return this.fh(plus(this.dh_1, match));
  }
  gh(matches, negative) {
    return new AndMatch(matches, negative);
  }
  fh(matches, negative, $super) {
    matches = matches === VOID ? this.dh_1 : matches;
    negative = negative === VOID ? this.eh_1 : negative;
    return $super === VOID ? this.gh(matches, negative) : $super.gh.call(this, matches, negative);
  }
  toString() {
    return 'AndMatch(matches=' + toString_1(this.dh_1) + ', negative=' + this.eh_1 + ')';
  }
  hashCode() {
    var result = hashCode(this.dh_1);
    result = imul_0(result, 31) + getBooleanHashCode(this.eh_1) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof AndMatch))
      return false;
    if (!equals(this.dh_1, other.dh_1))
      return false;
    if (!(this.eh_1 === other.eh_1))
      return false;
    return true;
  }
  get negative() {
    return this.ch();
  }
}
class OrMatch {
  constructor(matches, negative) {
    negative = negative === VOID ? false : negative;
    this.hh_1 = matches;
    this.ih_1 = negative;
  }
  ch() {
    return this.ih_1;
  }
  map(transform) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = this.hh_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList.m(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.f();
    while (_iterator__ex2g4s.g()) {
      var item = _iterator__ex2g4s.h();
      var tmp$ret$2 = item.map(transform);
      destination.o(tmp$ret$2);
    }
    return new OrMatch(destination, this.ih_1);
  }
  not() {
    return this.jh(VOID, !this.ih_1);
  }
  or(match) {
    return this.jh(plus(this.hh_1, match));
  }
  gh(matches, negative) {
    return new OrMatch(matches, negative);
  }
  jh(matches, negative, $super) {
    matches = matches === VOID ? this.hh_1 : matches;
    negative = negative === VOID ? this.ih_1 : negative;
    return $super === VOID ? this.gh(matches, negative) : $super.gh.call(this, matches, negative);
  }
  toString() {
    return 'OrMatch(matches=' + toString_1(this.hh_1) + ', negative=' + this.ih_1 + ')';
  }
  hashCode() {
    var result = hashCode(this.hh_1);
    result = imul_0(result, 31) + getBooleanHashCode(this.ih_1) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrMatch))
      return false;
    if (!equals(this.hh_1, other.hh_1))
      return false;
    if (!(this.ih_1 === other.ih_1))
      return false;
    return true;
  }
  get negative() {
    return this.ch();
  }
}
class SortDTO {}
class PageDTO {}
class Companion_14 {
  constructor() {
    Companion_instance_14 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.dsl.cqrs.page.Page', null, 2);
    tmp0_serialDesc.rf('total', false);
    tmp0_serialDesc.rf('items', false);
    this.ph_1 = tmp0_serialDesc;
  }
}
class Page {
  constructor(total, items) {
    Companion_getInstance_14();
    this.qh_1 = total;
    this.rh_1 = items;
  }
  nh() {
    return this.qh_1;
  }
  oh() {
    return this.rh_1;
  }
  static sh(seen0, total, items, serializationConstructorMarker) {
    Companion_getInstance_14();
    if (!(3 === (3 & seen0))) {
      throwMissingFieldException(seen0, 3, Companion_getInstance_14().ph_1);
    }
    var $this = createThis(this);
    $this.qh_1 = total;
    $this.rh_1 = items;
    return $this;
  }
  get total() {
    return this.nh();
  }
  get items() {
    return this.oh();
  }
}
class PageQueryDTO {}
class PageQueryResultDTO {}
class Companion_15 {
  constructor() {
    Companion_instance_15 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.uh_1 = [lazy(tmp_0, PageQuery$Companion$$childSerializers$_anonymous__54lki7)];
  }
}
class $serializer_0 {
  constructor() {
    $serializer_instance_0 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.dsl.cqrs.page.PageQuery', this, 1);
    tmp0_serialDesc.rf('pagination', false);
    this.vh_1 = tmp0_serialDesc;
  }
  sc() {
    return this.vh_1;
  }
  of() {
    var tmp0_cached = Companion_getInstance_15().uh_1;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [get_nullable(tmp0_cached[0].d1())];
  }
}
class PageQuery {
  constructor(pagination) {
    Companion_getInstance_15();
    this.wh_1 = pagination;
  }
  th() {
    return this.wh_1;
  }
  static xh(seen0, pagination, serializationConstructorMarker) {
    Companion_getInstance_15();
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_0().vh_1);
    }
    var $this = createThis(this);
    $this.wh_1 = pagination;
    return $this;
  }
  get pagination() {
    return this.th();
  }
}
class Companion_16 {
  constructor() {
    Companion_instance_16 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.dsl.cqrs.page.PageQueryResult', null, 3);
    tmp0_serialDesc.rf('pagination', false);
    tmp0_serialDesc.rf('total', false);
    tmp0_serialDesc.rf('items', false);
    this.yh_1 = tmp0_serialDesc;
  }
}
class PageQueryResult {
  constructor(pagination, total, items) {
    Companion_getInstance_16();
    this.zh_1 = pagination;
    this.ai_1 = total;
    this.bi_1 = items;
  }
  th() {
    return this.zh_1;
  }
  nh() {
    return this.ai_1;
  }
  oh() {
    return this.bi_1;
  }
  static ci(seen0, pagination, total, items, serializationConstructorMarker) {
    Companion_getInstance_16();
    if (!(7 === (7 & seen0))) {
      throwMissingFieldException(seen0, 7, Companion_getInstance_16().yh_1);
    }
    var $this = createThis(this);
    $this.zh_1 = pagination;
    $this.ai_1 = total;
    $this.bi_1 = items;
    return $this;
  }
  get pagination() {
    return this.th();
  }
  get total() {
    return this.nh();
  }
  get items() {
    return this.oh();
  }
}
class Companion_17 {}
class Pagination {}
class OffsetPaginationDTO {}
class PagePaginationDTO {}
class Companion_18 {}
class $serializer_1 {
  constructor() {
    $serializer_instance_1 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.dsl.cqrs.page.OffsetPagination', this, 2);
    tmp0_serialDesc.rf('offset', false);
    tmp0_serialDesc.rf('limit', false);
    this.gi_1 = tmp0_serialDesc;
  }
  sc() {
    return this.gi_1;
  }
  of() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [IntSerializer_getInstance(), IntSerializer_getInstance()];
  }
}
class OffsetPagination {
  constructor(offset, limit) {
    this.hi_1 = offset;
    this.ii_1 = limit;
  }
  di() {
    return this.hi_1;
  }
  ei() {
    return this.ii_1;
  }
  static ji(seen0, offset, limit, serializationConstructorMarker) {
    if (!(3 === (3 & seen0))) {
      throwMissingFieldException(seen0, 3, $serializer_getInstance_1().gi_1);
    }
    var $this = createThis(this);
    $this.hi_1 = offset;
    $this.ii_1 = limit;
    return $this;
  }
  get offset() {
    return this.di();
  }
  get limit() {
    return this.ei();
  }
}
class Companion_19 {}
class $serializer_2 {
  constructor() {
    $serializer_instance_2 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.dsl.cqrs.page.PagePagination', this, 2);
    tmp0_serialDesc.rf('page', false);
    tmp0_serialDesc.rf('size', false);
    this.ki_1 = tmp0_serialDesc;
  }
  sc() {
    return this.ki_1;
  }
  of() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [get_nullable(IntSerializer_getInstance()), get_nullable(IntSerializer_getInstance())];
  }
}
class PagePagination {
  constructor(page, size) {
    this.li_1 = page;
    this.mi_1 = size;
  }
  fi() {
    return this.li_1;
  }
  i() {
    return this.mi_1;
  }
  static ni(seen0, page, size, serializationConstructorMarker) {
    if (!(3 === (3 & seen0))) {
      throwMissingFieldException(seen0, 3, $serializer_getInstance_2().ki_1);
    }
    var $this = createThis(this);
    $this.li_1 = page;
    $this.mi_1 = size;
    return $this;
  }
  get page() {
    return this.fi();
  }
  get size() {
    return this.i();
  }
}
class AuthRealm {
  constructor(serverUrl, realmId, clientId, redirectUrl) {
    this.oi_1 = serverUrl;
    this.pi_1 = realmId;
    this.qi_1 = clientId;
    this.ri_1 = redirectUrl;
  }
  si() {
    return this.oi_1;
  }
  ti() {
    return this.pi_1;
  }
  ui() {
    return this.qi_1;
  }
  vi() {
    return this.ri_1;
  }
  get serverUrl() {
    return this.si();
  }
  get realmId() {
    return this.ti();
  }
  get clientId() {
    return this.ui();
  }
  get redirectUrl() {
    return this.vi();
  }
}
class AuthRealmPassword extends AuthRealm {
  constructor(serverUrl, realmId, redirectUrl, clientId, username, password) {
    super(serverUrl, realmId, clientId, redirectUrl);
    this.aj_1 = serverUrl;
    this.bj_1 = realmId;
    this.cj_1 = redirectUrl;
    this.dj_1 = clientId;
    this.username = username;
    this.password = password;
  }
  si() {
    return this.aj_1;
  }
  ti() {
    return this.bj_1;
  }
  vi() {
    return this.cj_1;
  }
  ui() {
    return this.dj_1;
  }
  ej() {
    return this.username;
  }
  fj() {
    return this.password;
  }
  bb() {
    return this.aj_1;
  }
  cb() {
    return this.bj_1;
  }
  gj() {
    return this.cj_1;
  }
  hj() {
    return this.dj_1;
  }
  ij() {
    return this.username;
  }
  jj() {
    return this.password;
  }
  kj(serverUrl, realmId, redirectUrl, clientId, username, password) {
    return new AuthRealmPassword(serverUrl, realmId, redirectUrl, clientId, username, password);
  }
  copy(serverUrl, realmId, redirectUrl, clientId, username, password, $super) {
    serverUrl = serverUrl === VOID ? this.aj_1 : serverUrl;
    realmId = realmId === VOID ? this.bj_1 : realmId;
    redirectUrl = redirectUrl === VOID ? this.cj_1 : redirectUrl;
    clientId = clientId === VOID ? this.dj_1 : clientId;
    username = username === VOID ? this.username : username;
    password = password === VOID ? this.password : password;
    return $super === VOID ? this.kj(serverUrl, realmId, redirectUrl, clientId, username, password) : $super.kj.call(this, serverUrl, realmId, redirectUrl, clientId, username, password);
  }
  toString() {
    return 'AuthRealmPassword(serverUrl=' + this.aj_1 + ', realmId=' + this.bj_1 + ', redirectUrl=' + this.cj_1 + ', clientId=' + this.dj_1 + ', username=' + this.username + ', password=' + this.password + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.aj_1);
    result = imul_0(result, 31) + getStringHashCode(this.bj_1) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.cj_1) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.dj_1) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.username) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.password) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof AuthRealmPassword))
      return false;
    if (!(this.aj_1 === other.aj_1))
      return false;
    if (!(this.bj_1 === other.bj_1))
      return false;
    if (!(this.cj_1 === other.cj_1))
      return false;
    if (!(this.dj_1 === other.dj_1))
      return false;
    if (!(this.username === other.username))
      return false;
    if (!(this.password === other.password))
      return false;
    return true;
  }
}
class AuthRealmClientSecret extends AuthRealm {
  constructor(serverUrl, realmId, clientId, redirectUrl, clientSecret, isPublic) {
    redirectUrl = redirectUrl === VOID ? null : redirectUrl;
    isPublic = isPublic === VOID ? false : isPublic;
    super(serverUrl, realmId, clientId, redirectUrl);
    this.pj_1 = serverUrl;
    this.qj_1 = realmId;
    this.rj_1 = clientId;
    this.sj_1 = redirectUrl;
    this.clientSecret = clientSecret;
    this.isPublic = isPublic;
  }
  si() {
    return this.pj_1;
  }
  ti() {
    return this.qj_1;
  }
  ui() {
    return this.rj_1;
  }
  vi() {
    return this.sj_1;
  }
  tj() {
    return this.clientSecret;
  }
  uj() {
    return this.isPublic;
  }
  bb() {
    return this.pj_1;
  }
  cb() {
    return this.qj_1;
  }
  gj() {
    return this.rj_1;
  }
  hj() {
    return this.sj_1;
  }
  ij() {
    return this.clientSecret;
  }
  jj() {
    return this.isPublic;
  }
  vj(serverUrl, realmId, clientId, redirectUrl, clientSecret, isPublic) {
    return new AuthRealmClientSecret(serverUrl, realmId, clientId, redirectUrl, clientSecret, isPublic);
  }
  copy(serverUrl, realmId, clientId, redirectUrl, clientSecret, isPublic, $super) {
    serverUrl = serverUrl === VOID ? this.pj_1 : serverUrl;
    realmId = realmId === VOID ? this.qj_1 : realmId;
    clientId = clientId === VOID ? this.rj_1 : clientId;
    redirectUrl = redirectUrl === VOID ? this.sj_1 : redirectUrl;
    clientSecret = clientSecret === VOID ? this.clientSecret : clientSecret;
    isPublic = isPublic === VOID ? this.isPublic : isPublic;
    return $super === VOID ? this.vj(serverUrl, realmId, clientId, redirectUrl, clientSecret, isPublic) : $super.vj.call(this, serverUrl, realmId, clientId, redirectUrl, clientSecret, isPublic);
  }
  toString() {
    return 'AuthRealmClientSecret(serverUrl=' + this.pj_1 + ', realmId=' + this.qj_1 + ', clientId=' + this.rj_1 + ', redirectUrl=' + this.sj_1 + ', clientSecret=' + this.clientSecret + ', isPublic=' + this.isPublic + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.pj_1);
    result = imul_0(result, 31) + getStringHashCode(this.qj_1) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.rj_1) | 0;
    result = imul_0(result, 31) + (this.sj_1 == null ? 0 : getStringHashCode(this.sj_1)) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.clientSecret) | 0;
    result = imul_0(result, 31) + getBooleanHashCode(this.isPublic) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof AuthRealmClientSecret))
      return false;
    if (!(this.pj_1 === other.pj_1))
      return false;
    if (!(this.qj_1 === other.qj_1))
      return false;
    if (!(this.rj_1 === other.rj_1))
      return false;
    if (!(this.sj_1 == other.sj_1))
      return false;
    if (!(this.clientSecret === other.clientSecret))
      return false;
    if (!(this.isPublic === other.isPublic))
      return false;
    return true;
  }
}
class Companion_20 {}
class $serializer_3 {
  constructor() {
    $serializer_instance_3 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.client.domain.TokenInfo', this, 7);
    tmp0_serialDesc.rf('access_token', false);
    tmp0_serialDesc.rf('expires_in', false);
    tmp0_serialDesc.rf('refresh_expires_in', true);
    tmp0_serialDesc.rf('token_type', false);
    tmp0_serialDesc.rf('scope', false);
    tmp0_serialDesc.rf('id_token', true);
    tmp0_serialDesc.rf('refresh_token', true);
    this.wj_1 = tmp0_serialDesc;
  }
  sc() {
    return this.wj_1;
  }
  of() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), IntSerializer_getInstance(), get_nullable(IntSerializer_getInstance()), StringSerializer_getInstance(), get_nullable(StringSerializer_getInstance()), get_nullable(StringSerializer_getInstance()), get_nullable(StringSerializer_getInstance())];
  }
}
class TokenInfo {
  constructor(accessToken, expiresIn, refreshExpiresIn, tokenType, scope, idToken, refreshToken) {
    refreshExpiresIn = refreshExpiresIn === VOID ? null : refreshExpiresIn;
    idToken = idToken === VOID ? null : idToken;
    refreshToken = refreshToken === VOID ? null : refreshToken;
    this.accessToken = accessToken;
    this.expiresIn = expiresIn;
    this.refreshExpiresIn = refreshExpiresIn;
    this.tokenType = tokenType;
    this.scope = scope;
    this.idToken = idToken;
    this.refreshToken = refreshToken;
  }
  xj() {
    return this.accessToken;
  }
  yj() {
    return this.expiresIn;
  }
  zj() {
    return this.refreshExpiresIn;
  }
  ak() {
    return this.tokenType;
  }
  bk() {
    return this.scope;
  }
  ck() {
    return this.idToken;
  }
  dk() {
    return this.refreshToken;
  }
  bb() {
    return this.accessToken;
  }
  cb() {
    return this.expiresIn;
  }
  gj() {
    return this.refreshExpiresIn;
  }
  hj() {
    return this.tokenType;
  }
  ij() {
    return this.scope;
  }
  jj() {
    return this.idToken;
  }
  ek() {
    return this.refreshToken;
  }
  fk(accessToken, expiresIn, refreshExpiresIn, tokenType, scope, idToken, refreshToken) {
    return new TokenInfo(accessToken, expiresIn, refreshExpiresIn, tokenType, scope, idToken, refreshToken);
  }
  copy(accessToken, expiresIn, refreshExpiresIn, tokenType, scope, idToken, refreshToken, $super) {
    accessToken = accessToken === VOID ? this.accessToken : accessToken;
    expiresIn = expiresIn === VOID ? this.expiresIn : expiresIn;
    refreshExpiresIn = refreshExpiresIn === VOID ? this.refreshExpiresIn : refreshExpiresIn;
    tokenType = tokenType === VOID ? this.tokenType : tokenType;
    scope = scope === VOID ? this.scope : scope;
    idToken = idToken === VOID ? this.idToken : idToken;
    refreshToken = refreshToken === VOID ? this.refreshToken : refreshToken;
    return $super === VOID ? this.fk(accessToken, expiresIn, refreshExpiresIn, tokenType, scope, idToken, refreshToken) : $super.fk.call(this, accessToken, expiresIn, refreshExpiresIn, tokenType, scope, idToken, refreshToken);
  }
  toString() {
    return 'TokenInfo(accessToken=' + this.accessToken + ', expiresIn=' + this.expiresIn + ', refreshExpiresIn=' + this.refreshExpiresIn + ', tokenType=' + this.tokenType + ', scope=' + this.scope + ', idToken=' + this.idToken + ', refreshToken=' + this.refreshToken + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.accessToken);
    result = imul_0(result, 31) + this.expiresIn | 0;
    result = imul_0(result, 31) + (this.refreshExpiresIn == null ? 0 : this.refreshExpiresIn) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.tokenType) | 0;
    result = imul_0(result, 31) + (this.scope == null ? 0 : getStringHashCode(this.scope)) | 0;
    result = imul_0(result, 31) + (this.idToken == null ? 0 : getStringHashCode(this.idToken)) | 0;
    result = imul_0(result, 31) + (this.refreshToken == null ? 0 : getStringHashCode(this.refreshToken)) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof TokenInfo))
      return false;
    if (!(this.accessToken === other.accessToken))
      return false;
    if (!(this.expiresIn === other.expiresIn))
      return false;
    if (!(this.refreshExpiresIn == other.refreshExpiresIn))
      return false;
    if (!(this.tokenType === other.tokenType))
      return false;
    if (!(this.scope == other.scope))
      return false;
    if (!(this.idToken == other.idToken))
      return false;
    if (!(this.refreshToken == other.refreshToken))
      return false;
    return true;
  }
  static gk(seen0, accessToken, expiresIn, refreshExpiresIn, tokenType, scope, idToken, refreshToken, serializationConstructorMarker) {
    if (!(27 === (27 & seen0))) {
      throwMissingFieldException(seen0, 27, $serializer_getInstance_3().wj_1);
    }
    var $this = createThis(this);
    $this.accessToken = accessToken;
    $this.expiresIn = expiresIn;
    if (0 === (seen0 & 4))
      $this.refreshExpiresIn = null;
    else
      $this.refreshExpiresIn = refreshExpiresIn;
    $this.tokenType = tokenType;
    $this.scope = scope;
    if (0 === (seen0 & 32))
      $this.idToken = null;
    else
      $this.idToken = idToken;
    if (0 === (seen0 & 64))
      $this.refreshToken = null;
    else
      $this.refreshToken = refreshToken;
    return $this;
  }
}
class AuthedUserDTO {}
class ImPermission extends Enum {
  constructor(name, ordinal, identifier) {
    super(name, ordinal);
    this.identifier = identifier;
  }
  hk() {
    return this.identifier;
  }
  get name() {
    return this.k1();
  }
  get ordinal() {
    return this.l1();
  }
}
class ExceptionCodes {
  privilegeWrongTarget() {
    return 1000;
  }
}
class AddressDTO {}
class Companion_21 {}
class $serializer_4 {
  constructor() {
    $serializer_instance_4 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.commons.model.Address', this, 3);
    tmp0_serialDesc.rf('street', false);
    tmp0_serialDesc.rf('postalCode', false);
    tmp0_serialDesc.rf('city', false);
    this.qk_1 = tmp0_serialDesc;
  }
  sc() {
    return this.qk_1;
  }
  of() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), StringSerializer_getInstance(), StringSerializer_getInstance()];
  }
}
class Address {
  constructor(street, postalCode, city) {
    this.rk_1 = street;
    this.sk_1 = postalCode;
    this.tk_1 = city;
  }
  nk() {
    return this.rk_1;
  }
  ok() {
    return this.sk_1;
  }
  pk() {
    return this.tk_1;
  }
  bb() {
    return this.rk_1;
  }
  cb() {
    return this.sk_1;
  }
  gj() {
    return this.tk_1;
  }
  uk(street, postalCode, city) {
    return new Address(street, postalCode, city);
  }
  copy(street, postalCode, city, $super) {
    street = street === VOID ? this.rk_1 : street;
    postalCode = postalCode === VOID ? this.sk_1 : postalCode;
    city = city === VOID ? this.tk_1 : city;
    return $super === VOID ? this.uk(street, postalCode, city) : $super.uk.call(this, street, postalCode, city);
  }
  toString() {
    return 'Address(street=' + this.rk_1 + ', postalCode=' + this.sk_1 + ', city=' + this.tk_1 + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.rk_1);
    result = imul_0(result, 31) + getStringHashCode(this.sk_1) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.tk_1) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof Address))
      return false;
    if (!(this.rk_1 === other.rk_1))
      return false;
    if (!(this.sk_1 === other.sk_1))
      return false;
    if (!(this.tk_1 === other.tk_1))
      return false;
    return true;
  }
  static vk(seen0, street, postalCode, city, serializationConstructorMarker) {
    if (!(7 === (7 & seen0))) {
      throwMissingFieldException(seen0, 7, $serializer_getInstance_4().qk_1);
    }
    var $this = createThis(this);
    $this.rk_1 = street;
    $this.sk_1 = postalCode;
    $this.tk_1 = city;
    return $this;
  }
  get street() {
    return this.nk();
  }
  get postalCode() {
    return this.ok();
  }
  get city() {
    return this.pk();
  }
}
//endregion
function get_indices(_this__u8e3s4) {
  return new IntRange(0, get_lastIndex(_this__u8e3s4));
}
function indexOf(_this__u8e3s4, element) {
  if (element == null) {
    var inductionVariable = 0;
    var last = _this__u8e3s4.length - 1 | 0;
    if (inductionVariable <= last)
      do {
        var index = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        if (_this__u8e3s4[index] == null) {
          return index;
        }
      }
       while (inductionVariable <= last);
  } else {
    var inductionVariable_0 = 0;
    var last_0 = _this__u8e3s4.length - 1 | 0;
    if (inductionVariable_0 <= last_0)
      do {
        var index_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + 1 | 0;
        if (equals(element, _this__u8e3s4[index_0])) {
          return index_0;
        }
      }
       while (inductionVariable_0 <= last_0);
  }
  return -1;
}
function lastIndexOf(_this__u8e3s4, element) {
  if (element == null) {
    var inductionVariable = _this__u8e3s4.length - 1 | 0;
    if (0 <= inductionVariable)
      do {
        var index = inductionVariable;
        inductionVariable = inductionVariable + -1 | 0;
        if (_this__u8e3s4[index] == null) {
          return index;
        }
      }
       while (0 <= inductionVariable);
  } else {
    var inductionVariable_0 = _this__u8e3s4.length - 1 | 0;
    if (0 <= inductionVariable_0)
      do {
        var index_0 = inductionVariable_0;
        inductionVariable_0 = inductionVariable_0 + -1 | 0;
        if (equals(element, _this__u8e3s4[index_0])) {
          return index_0;
        }
      }
       while (0 <= inductionVariable_0);
  }
  return -1;
}
function get_lastIndex(_this__u8e3s4) {
  return _this__u8e3s4.length - 1 | 0;
}
function joinToString(_this__u8e3s4, separator, prefix, postfix, limit, truncated, transform) {
  separator = separator === VOID ? ', ' : separator;
  prefix = prefix === VOID ? '' : prefix;
  postfix = postfix === VOID ? '' : postfix;
  limit = limit === VOID ? -1 : limit;
  truncated = truncated === VOID ? '...' : truncated;
  transform = transform === VOID ? null : transform;
  return joinTo(_this__u8e3s4, StringBuilder.d(), separator, prefix, postfix, limit, truncated, transform).toString();
}
function joinTo(_this__u8e3s4, buffer, separator, prefix, postfix, limit, truncated, transform) {
  separator = separator === VOID ? ', ' : separator;
  prefix = prefix === VOID ? '' : prefix;
  postfix = postfix === VOID ? '' : postfix;
  limit = limit === VOID ? -1 : limit;
  truncated = truncated === VOID ? '...' : truncated;
  transform = transform === VOID ? null : transform;
  buffer.e(prefix);
  var count = 0;
  var inductionVariable = 0;
  var last = _this__u8e3s4.length;
  $l$loop: while (inductionVariable < last) {
    var element = _this__u8e3s4[inductionVariable];
    inductionVariable = inductionVariable + 1 | 0;
    count = count + 1 | 0;
    if (count > 1) {
      buffer.e(separator);
    }
    if (limit < 0 || count <= limit) {
      appendElement(buffer, element, transform);
    } else
      break $l$loop;
  }
  if (limit >= 0 && count > limit) {
    buffer.e(truncated);
  }
  buffer.e(postfix);
  return buffer;
}
function toList(_this__u8e3s4) {
  var tmp;
  switch (_this__u8e3s4.length) {
    case 0:
      tmp = emptyList();
      break;
    case 1:
      tmp = listOf(_this__u8e3s4[0]);
      break;
    default:
      // Inline function 'kotlin.collections.copyOf' call

      // Inline function 'kotlin.collections.copyOf' call

      // Inline function 'kotlin.js.asDynamic' call

      var tmp$ret$0 = _this__u8e3s4.slice();
      tmp = asList(tmp$ret$0);
      break;
  }
  return tmp;
}
function withIndex(_this__u8e3s4) {
  return new IndexingIterable(withIndex$lambda(_this__u8e3s4));
}
function joinToString_0(_this__u8e3s4, separator, prefix, postfix, limit, truncated, transform) {
  separator = separator === VOID ? ', ' : separator;
  prefix = prefix === VOID ? '' : prefix;
  postfix = postfix === VOID ? '' : postfix;
  limit = limit === VOID ? -1 : limit;
  truncated = truncated === VOID ? '...' : truncated;
  transform = transform === VOID ? null : transform;
  return joinTo_0(_this__u8e3s4, StringBuilder.d(), separator, prefix, postfix, limit, truncated, transform).toString();
}
function joinTo_0(_this__u8e3s4, buffer, separator, prefix, postfix, limit, truncated, transform) {
  separator = separator === VOID ? ', ' : separator;
  prefix = prefix === VOID ? '' : prefix;
  postfix = postfix === VOID ? '' : postfix;
  limit = limit === VOID ? -1 : limit;
  truncated = truncated === VOID ? '...' : truncated;
  transform = transform === VOID ? null : transform;
  buffer.e(prefix);
  var count = 0;
  var inductionVariable = 0;
  var last = _this__u8e3s4.length;
  $l$loop: while (inductionVariable < last) {
    var element = _this__u8e3s4[inductionVariable];
    inductionVariable = inductionVariable + 1 | 0;
    count = count + 1 | 0;
    if (count > 1) {
      buffer.e(separator);
    }
    if (limit < 0 || count <= limit) {
      if (!(transform == null))
        buffer.e(transform(element));
      else
        buffer.e(element.toString());
    } else
      break $l$loop;
  }
  if (limit >= 0 && count > limit) {
    buffer.e(truncated);
  }
  buffer.e(postfix);
  return buffer;
}
function withIndex$lambda($this_withIndex) {
  return () => arrayIterator($this_withIndex);
}
function joinToString_1(_this__u8e3s4, separator, prefix, postfix, limit, truncated, transform) {
  separator = separator === VOID ? ', ' : separator;
  prefix = prefix === VOID ? '' : prefix;
  postfix = postfix === VOID ? '' : postfix;
  limit = limit === VOID ? -1 : limit;
  truncated = truncated === VOID ? '...' : truncated;
  transform = transform === VOID ? null : transform;
  return joinTo_1(_this__u8e3s4, StringBuilder.d(), separator, prefix, postfix, limit, truncated, transform).toString();
}
function joinTo_1(_this__u8e3s4, buffer, separator, prefix, postfix, limit, truncated, transform) {
  separator = separator === VOID ? ', ' : separator;
  prefix = prefix === VOID ? '' : prefix;
  postfix = postfix === VOID ? '' : postfix;
  limit = limit === VOID ? -1 : limit;
  truncated = truncated === VOID ? '...' : truncated;
  transform = transform === VOID ? null : transform;
  buffer.e(prefix);
  var count = 0;
  var _iterator__ex2g4s = _this__u8e3s4.f();
  $l$loop: while (_iterator__ex2g4s.g()) {
    var element = _iterator__ex2g4s.h();
    count = count + 1 | 0;
    if (count > 1) {
      buffer.e(separator);
    }
    if (limit < 0 || count <= limit) {
      appendElement(buffer, element, transform);
    } else
      break $l$loop;
  }
  if (limit >= 0 && count > limit) {
    buffer.e(truncated);
  }
  buffer.e(postfix);
  return buffer;
}
function plus(_this__u8e3s4, element) {
  var result = ArrayList.m(_this__u8e3s4.i() + 1 | 0);
  result.n(_this__u8e3s4);
  result.o(element);
  return result;
}
function toHashSet(_this__u8e3s4) {
  return toCollection(_this__u8e3s4, HashSet.q(mapCapacity(collectionSizeOrDefault(_this__u8e3s4, 12))));
}
function toBooleanArray(_this__u8e3s4) {
  var result = booleanArray(_this__u8e3s4.i());
  var index = 0;
  var _iterator__ex2g4s = _this__u8e3s4.f();
  while (_iterator__ex2g4s.g()) {
    var element = _iterator__ex2g4s.h();
    var _unary__edvuaz = index;
    index = _unary__edvuaz + 1 | 0;
    result[_unary__edvuaz] = element;
  }
  return result;
}
function toCollection(_this__u8e3s4, destination) {
  var _iterator__ex2g4s = _this__u8e3s4.f();
  while (_iterator__ex2g4s.g()) {
    var item = _iterator__ex2g4s.h();
    destination.o(item);
  }
  return destination;
}
function until(_this__u8e3s4, to) {
  if (to <= -2147483648)
    return Companion_getInstance_6().r_1;
  return numberRangeToNumber(_this__u8e3s4, to - 1 | 0);
}
function coerceAtMost(_this__u8e3s4, maximumValue) {
  return _this__u8e3s4 > maximumValue ? maximumValue : _this__u8e3s4;
}
function coerceAtLeast(_this__u8e3s4, minimumValue) {
  return _this__u8e3s4 < minimumValue ? minimumValue : _this__u8e3s4;
}
function _Char___init__impl__6a9atx(value) {
  return value;
}
function _get_value__a43j40($this) {
  return $this;
}
function _Char___init__impl__6a9atx_0(code) {
  // Inline function 'kotlin.UShort.toInt' call
  var tmp$ret$0 = _UShort___get_data__impl__g0245(code) & 65535;
  return _Char___init__impl__6a9atx(tmp$ret$0);
}
function Char__compareTo_impl_ypi4mb($this, other) {
  return _get_value__a43j40($this) - _get_value__a43j40(other) | 0;
}
function Char__toInt_impl_vasixd($this) {
  return _get_value__a43j40($this);
}
function toString($this) {
  // Inline function 'kotlin.js.unsafeCast' call
  return String.fromCharCode(_get_value__a43j40($this));
}
var Companion_instance;
function Companion_getInstance() {
  return Companion_instance;
}
function fromJsArray(array) {
  return Companion_instance.v(array);
}
var Companion_instance_0;
function Companion_getInstance_0() {
  return Companion_instance_0;
}
function toString_0(_this__u8e3s4) {
  var tmp1_elvis_lhs = _this__u8e3s4 == null ? null : toString_1(_this__u8e3s4);
  return tmp1_elvis_lhs == null ? 'null' : tmp1_elvis_lhs;
}
function abs(_this__u8e3s4) {
  var tmp;
  // Inline function 'kotlin.js.internal.isNegative' call
  if (_this__u8e3s4 < 0) {
    // Inline function 'kotlin.js.internal.unaryMinus' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp = -_this__u8e3s4;
  } else {
    tmp = _this__u8e3s4;
  }
  return tmp;
}
function arrayIterator(array) {
  return new arrayIterator$1(array);
}
function booleanArray(size) {
  var tmp0 = 'BooleanArray';
  // Inline function 'withType' call
  var array = fillArrayVal(Array(size), false);
  array.$type$ = tmp0;
  // Inline function 'kotlin.js.unsafeCast' call
  return array;
}
function fillArrayVal(array, initValue) {
  var inductionVariable = 0;
  var last = array.length - 1 | 0;
  if (inductionVariable <= last)
    do {
      var i = inductionVariable;
      inductionVariable = inductionVariable + 1 | 0;
      array[i] = initValue;
    }
     while (!(i === last));
  return array;
}
function get_buf() {
  _init_properties_bitUtils_kt__nfcg4k();
  return buf;
}
var buf;
function get_bufFloat64() {
  _init_properties_bitUtils_kt__nfcg4k();
  return bufFloat64;
}
var bufFloat64;
var bufFloat32;
function get_bufInt32() {
  _init_properties_bitUtils_kt__nfcg4k();
  return bufInt32;
}
var bufInt32;
function get_lowIndex() {
  _init_properties_bitUtils_kt__nfcg4k();
  return lowIndex;
}
var lowIndex;
function get_highIndex() {
  _init_properties_bitUtils_kt__nfcg4k();
  return highIndex;
}
var highIndex;
function getNumberHashCode(obj) {
  _init_properties_bitUtils_kt__nfcg4k();
  // Inline function 'kotlin.js.jsBitwiseOr' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  if ((obj | 0) === obj) {
    return numberToInt(obj);
  }
  get_bufFloat64()[0] = obj;
  return imul_0(get_bufInt32()[get_highIndex()], 31) + get_bufInt32()[get_lowIndex()] | 0;
}
var properties_initialized_bitUtils_kt_i2bo3e;
function _init_properties_bitUtils_kt__nfcg4k() {
  if (!properties_initialized_bitUtils_kt_i2bo3e) {
    properties_initialized_bitUtils_kt_i2bo3e = true;
    buf = new ArrayBuffer(8);
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    bufFloat64 = new Float64Array(get_buf());
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    bufFloat32 = new Float32Array(get_buf());
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    bufInt32 = new Int32Array(get_buf());
    // Inline function 'kotlin.run' call
    get_bufFloat64()[0] = -1.0;
    lowIndex = !(get_bufInt32()[0] === 0) ? 1 : 0;
    highIndex = 1 - get_lowIndex() | 0;
  }
}
var ZERO;
function get_ONE() {
  _init_properties_boxedLong_kt__v24qrw();
  return ONE;
}
var ONE;
var NEG_ONE;
var MAX_VALUE;
var MIN_VALUE;
var TWO_PWR_24_;
var longArrayClass;
function fromInt(value) {
  _init_properties_boxedLong_kt__v24qrw();
  return longFromTwoInts(value, value < 0 ? -1 : 0);
}
function longArrayClass$lambda(it) {
  _init_properties_boxedLong_kt__v24qrw();
  return !(it == null) ? isLongArray(it) : false;
}
var properties_initialized_boxedLong_kt_lfwt2;
function _init_properties_boxedLong_kt__v24qrw() {
  if (!properties_initialized_boxedLong_kt_lfwt2) {
    properties_initialized_boxedLong_kt_lfwt2 = true;
    ZERO = fromInt(0);
    ONE = fromInt(1);
    NEG_ONE = fromInt(-1);
    MAX_VALUE = longFromTwoInts(-1, 2147483647);
    MIN_VALUE = longFromTwoInts(0, -2147483648);
    TWO_PWR_24_ = fromInt(16777216);
    // Inline function 'kotlin.js.unsafeCast' call
    var tmp = Array;
    longArrayClass = new PrimitiveKClassImpl(tmp, 'LongArray', longArrayClass$lambda);
  }
}
function charSequenceGet(a, index) {
  var tmp;
  if (isString(a)) {
    tmp = charCodeAt(a, index);
  } else {
    tmp = a.b(index);
  }
  return tmp;
}
function isString(a) {
  return typeof a === 'string';
}
function charCodeAt(_this__u8e3s4, index) {
  // Inline function 'kotlin.js.asDynamic' call
  return _this__u8e3s4.charCodeAt(index);
}
function charSequenceLength(a) {
  var tmp;
  if (isString(a)) {
    // Inline function 'kotlin.js.asDynamic' call
    // Inline function 'kotlin.js.unsafeCast' call
    tmp = a.length;
  } else {
    tmp = a.a();
  }
  return tmp;
}
function arrayToString(array) {
  return joinToString(array, ', ', '[', ']', VOID, VOID, arrayToString$lambda);
}
function contentEqualsInternal(_this__u8e3s4, other) {
  // Inline function 'kotlin.js.asDynamic' call
  var a = _this__u8e3s4;
  // Inline function 'kotlin.js.asDynamic' call
  var b = other;
  if (a === b)
    return true;
  if (a == null || b == null || !isArrayish(b) || a.length != b.length)
    return false;
  var inductionVariable = 0;
  var last = a.length;
  if (inductionVariable < last)
    do {
      var i = inductionVariable;
      inductionVariable = inductionVariable + 1 | 0;
      if (!equals(a[i], b[i])) {
        return false;
      }
    }
     while (inductionVariable < last);
  return true;
}
function contentHashCodeInternal(_this__u8e3s4) {
  // Inline function 'kotlin.js.asDynamic' call
  var a = _this__u8e3s4;
  if (a == null)
    return 0;
  var result = 1;
  var inductionVariable = 0;
  var last = a.length;
  if (inductionVariable < last)
    do {
      var i = inductionVariable;
      inductionVariable = inductionVariable + 1 | 0;
      result = imul_0(result, 31) + hashCode(a[i]) | 0;
    }
     while (inductionVariable < last);
  return result;
}
function arrayToString$lambda(it) {
  return toString_1(it);
}
function createJsReadonlyArrayViewFrom(list) {
  var tmp = createJsReadonlyArrayViewFrom$lambda(list);
  var tmp_0 = createJsReadonlyArrayViewFrom$lambda_0(list);
  // Inline function 'kotlin.js.asDynamic' call
  var tmp_1 = UNSUPPORTED_OPERATION$ref();
  // Inline function 'kotlin.js.asDynamic' call
  var tmp_2 = UNSUPPORTED_OPERATION$ref();
  // Inline function 'kotlin.js.asDynamic' call
  var tmp$ret$2 = UNSUPPORTED_OPERATION$ref();
  return createJsArrayViewWith(tmp, tmp_0, tmp_1, tmp_2, tmp$ret$2);
}
function createListFrom(array) {
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.unsafeCast' call
  var tmp$ret$1 = array.slice();
  return ArrayList.v1(tmp$ret$1).w1();
}
function UNSUPPORTED_OPERATION() {
  throw UnsupportedOperationException.a2();
}
function createJsArrayViewWith(listSize, listGet, listSet, listDecreaseSize, listIncreaseSize) {
  var arrayView = new Array();
  var tmp = Object;
  // Inline function 'kotlin.js.asDynamic' call
  var tmp$ret$0 = JsArrayView;
  tmp.setPrototypeOf(arrayView, tmp$ret$0.prototype);
  return new Proxy(arrayView, {get: function (target, prop, receiver) {
    if (prop === 'length')
      return listSize();
    var type = typeof prop;
    var index = type === 'string' || type === 'number' ? +prop : undefined;
    if (!isNaN(index))
      return listGet(index);
    return target[prop];
  }, has: function (target, key) {
    return !isNaN(key) && key < listSize();
  }, set: function (obj, prop, value) {
    if (prop === 'length') {
      var size = listSize();
      var newSize = type === 'string' || type === 'number' ? +prop : undefined;
      if (isNaN(newSize))
        throw new RangeError('invalid array length');
      if (newSize < size)
        listDecreaseSize(size - newSize);
      else
        listIncreaseSize(newSize - size);
      return true;
    }
    var type = typeof prop;
    var index = type === 'string' || type === 'number' ? +prop : undefined;
    if (isNaN(index))
      return false;
    listSet(index, value);
    return true;
  }});
}
function createJsReadonlyArrayViewFrom$lambda($list) {
  return () => $list.i();
}
function createJsReadonlyArrayViewFrom$lambda_0($list) {
  return (i) => $list.w(i);
}
function UNSUPPORTED_OPERATION$ref() {
  return constructCallableReference(() => {
    UNSUPPORTED_OPERATION();
    return Unit_instance;
  }, 0, 0, 0, 'UNSUPPORTED_OPERATION');
}
function compareTo(a, b) {
  var tmp;
  switch (typeof a) {
    case 'number':
      var tmp_0;
      if (typeof b === 'number') {
        tmp_0 = doubleCompareTo(a, b);
      } else {
        if (!(b == null) ? typeof b === 'bigint' : false) {
          tmp_0 = doubleCompareTo(a, toNumber(b));
        } else {
          tmp_0 = primitiveCompareTo(a, b);
        }
      }

      tmp = tmp_0;
      break;
    case 'string':
    case 'boolean':
    case 'bigint':
      tmp = primitiveCompareTo(a, b);
      break;
    default:
      tmp = compareToDoNotIntrinsicify(a, b);
      break;
  }
  return tmp;
}
function doubleCompareTo(a, b) {
  var tmp;
  if (a < b) {
    tmp = -1;
  } else if (a > b) {
    tmp = 1;
  } else if (a === b) {
    var tmp_0;
    if (a !== 0) {
      tmp_0 = 0;
    } else {
      // Inline function 'kotlin.js.asDynamic' call
      var ia = 1 / a;
      var tmp_1;
      // Inline function 'kotlin.js.asDynamic' call
      if (ia === 1 / b) {
        tmp_1 = 0;
      } else {
        if (ia < 0) {
          tmp_1 = -1;
        } else {
          tmp_1 = 1;
        }
      }
      tmp_0 = tmp_1;
    }
    tmp = tmp_0;
  } else if (a !== a) {
    tmp = b !== b ? 0 : 1;
  } else {
    tmp = -1;
  }
  return tmp;
}
function primitiveCompareTo(a, b) {
  return a < b ? -1 : a > b ? 1 : 0;
}
function compareToDoNotIntrinsicify(a, b) {
  return a.n1(b);
}
function identityHashCode(obj) {
  return getObjectHashCode(obj);
}
function defineProp(obj, name, getter, setter, enumerable) {
  return Object.defineProperty(obj, name, {configurable: true, get: getter, set: setter, enumerable: enumerable});
}
function getObjectHashCode(obj) {
  // Inline function 'kotlin.js.jsIn' call
  if (!('kotlinHashCodeValue$' in obj)) {
    var hash = calculateRandomHash();
    var descriptor = new Object();
    descriptor.value = hash;
    descriptor.enumerable = false;
    Object.defineProperty(obj, 'kotlinHashCodeValue$', descriptor);
  }
  // Inline function 'kotlin.js.unsafeCast' call
  return obj['kotlinHashCodeValue$'];
}
function calculateRandomHash() {
  // Inline function 'kotlin.js.jsBitwiseOr' call
  return Math.random() * 4.294967296E9 | 0;
}
function toString_1(o) {
  var tmp;
  if (o == null) {
    tmp = 'null';
  } else if (isArrayish(o)) {
    tmp = '[...]';
  } else if (!(typeof o.toString === 'function')) {
    tmp = anyToString(o);
  } else {
    // Inline function 'kotlin.js.unsafeCast' call
    tmp = o.toString();
  }
  return tmp;
}
function anyToString(o) {
  return Object.prototype.toString.call(o);
}
function equals(obj1, obj2) {
  if (obj1 == null) {
    return obj2 == null;
  }
  if (obj2 == null) {
    return false;
  }
  if (typeof obj1 === 'object' && typeof obj1.equals === 'function') {
    return obj1.equals(obj2);
  }
  if (obj1 !== obj1) {
    return obj2 !== obj2;
  }
  if (typeof obj1 === 'number' && typeof obj2 === 'number') {
    var tmp;
    if (obj1 === obj2) {
      var tmp_0;
      if (obj1 !== 0) {
        tmp_0 = true;
      } else {
        // Inline function 'kotlin.js.asDynamic' call
        var tmp_1 = 1 / obj1;
        // Inline function 'kotlin.js.asDynamic' call
        tmp_0 = tmp_1 === 1 / obj2;
      }
      tmp = tmp_0;
    } else {
      tmp = false;
    }
    return tmp;
  }
  if (isCallableReference(obj1) && isCallableReference(obj2)) {
    if (obj1 === obj2)
      return true;
    if (obj1.$id != obj2.$id)
      return false;
    if (obj1.$flags != obj2.$flags)
      return false;
    if (obj1.$arity != obj2.$arity)
      return false;
    if (obj1.$bound == null && obj2.$bound == null)
      return true;
    if (obj1.$bound === obj2.$bound)
      return true;
    if (!isJsArray(obj1.$bound) || !isJsArray(obj2.$bound))
      return false;
    // Inline function 'kotlin.js.unsafeCast' call
    var bound1 = obj1.$bound;
    // Inline function 'kotlin.js.unsafeCast' call
    var bound2 = obj2.$bound;
    return contentEqualsInternal(bound1, bound2);
  }
  return obj1 === obj2;
}
function hashCode(obj) {
  if (obj == null)
    return 0;
  var typeOf = typeof obj;
  var tmp;
  switch (typeOf) {
    case 'object':
      tmp = 'function' === typeof obj.hashCode ? obj.hashCode() : getObjectHashCode(obj);
      break;
    case 'function':
      tmp = isCallableReference(obj) ? getCallableReferenceHashCode(obj) : getObjectHashCode(obj);
      break;
    case 'number':
      tmp = getNumberHashCode(obj);
      break;
    case 'boolean':
      // Inline function 'kotlin.js.unsafeCast' call

      tmp = getBooleanHashCode(obj);
      break;
    case 'string':
      tmp = getStringHashCode(String(obj));
      break;
    case 'bigint':
      // Inline function 'kotlin.js.unsafeCast' call

      tmp = getBigIntHashCode(obj);
      break;
    case 'symbol':
      tmp = getSymbolHashCode(obj);
      break;
    default:
      tmp = function () {
        throw new Error('Unexpected typeof `' + typeOf + '`');
      }();
      break;
  }
  return tmp;
}
function getCallableReferenceHashCode(obj) {
  // Inline function 'kotlin.js.unsafeCast' call
  var hash = obj.$flags;
  hash = imul_0(31, hash) + hashCode(obj.$id) | 0;
  var tmp = imul_0(31, hash);
  var tmp0_elvis_lhs = obj.$arity;
  // Inline function 'kotlin.js.unsafeCast' call
  hash = tmp + (tmp0_elvis_lhs == null ? -1 : tmp0_elvis_lhs) | 0;
  var bound = obj.$bound;
  if (bound != null && isJsArray(bound)) {
    // Inline function 'kotlin.js.unsafeCast' call
    var boundArray = bound;
    hash = imul_0(31, hash) + contentHashCodeInternal(boundArray) | 0;
  }
  return hash;
}
function getBooleanHashCode(value) {
  return value ? 1231 : 1237;
}
function getStringHashCode(str) {
  var hash = 0;
  var length = str.length;
  var inductionVariable = 0;
  var last = length - 1 | 0;
  if (inductionVariable <= last)
    do {
      var i = inductionVariable;
      inductionVariable = inductionVariable + 1 | 0;
      // Inline function 'kotlin.js.asDynamic' call
      var code = str.charCodeAt(i);
      hash = imul_0(hash, 31) + code | 0;
    }
     while (!(i === last));
  return hash;
}
function getBigIntHashCode(value) {
  var shiftNumber = BigInt(32);
  var mask = BigInt(4.294967295E9);
  var bigNumber = abs(value);
  var hashCode = 0;
  var tmp;
  // Inline function 'kotlin.js.internal.isNegative' call
  if (value < 0) {
    tmp = -1;
  } else {
    tmp = 1;
  }
  var signum = tmp;
  $l$loop: while (true) {
    // Inline function 'kotlin.js.internal.isZero' call
    if (!!(bigNumber == 0)) {
      break $l$loop;
    }
    // Inline function 'kotlin.js.internal.and' call
    // Inline function 'kotlin.js.jsBitwiseAnd' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    // Inline function 'kotlin.js.internal.toNumber' call
    var self_0 = bigNumber & mask;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var chunk = Number(self_0);
    hashCode = imul_0(31, hashCode) + chunk | 0;
    // Inline function 'kotlin.js.internal.shr' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    bigNumber = bigNumber >> shiftNumber;
  }
  return imul_0(hashCode, signum);
}
function getSymbolHashCode(value) {
  var hashCodeMap = symbolIsSharable(value) ? getSymbolMap() : getSymbolWeakMap();
  var cachedHashCode = hashCodeMap.get(value);
  if (cachedHashCode !== VOID)
    return cachedHashCode;
  var hash = calculateRandomHash();
  hashCodeMap.set(value, hash);
  return hash;
}
function symbolIsSharable(symbol) {
  return Symbol.keyFor(symbol) != VOID;
}
function getSymbolMap() {
  if (symbolMap === VOID) {
    symbolMap = new Map();
  }
  return symbolMap;
}
function getSymbolWeakMap() {
  if (symbolWeakMap === VOID) {
    symbolWeakMap = new WeakMap();
  }
  return symbolWeakMap;
}
var symbolMap;
var symbolWeakMap;
function unboxIntrinsic(x) {
  // Inline function 'kotlin.error' call
  var message = 'Should be lowered';
  throw IllegalStateException.e2(toString_1(message));
}
function captureStack(instance, constructorFunction) {
  if (Error.captureStackTrace != null) {
    Error.captureStackTrace(instance, constructorFunction);
  } else {
    // Inline function 'kotlin.js.asDynamic' call
    instance.stack = (new Error()).stack;
  }
}
function protoOf(constructor) {
  return constructor.prototype;
}
function createThis(ctor, box) {
  var self_0 = Object.create(ctor.prototype);
  boxApply(self_0, box);
  return self_0;
}
function boxApply(self_0, box) {
  if (box !== VOID) {
    Object.assign(self_0, box);
  }
}
function createExternalThis(ctor, superExternalCtor, parameters, box) {
  var tmp;
  if (box === VOID) {
    tmp = ctor;
  } else {
    var newCtor = class extends ctor {}
    Object.assign(newCtor.prototype, box);
    newCtor.constructor = ctor;
    tmp = newCtor;
  }
  var selfCtor = tmp;
  return Reflect.construct(superExternalCtor, parameters, selfCtor);
}
function isUndefined(value) {
  return value === VOID;
}
function setupCauseParameter(cause) {
  return {cause: cause};
}
function setPropertiesToThrowableInstance(this_, message, cause) {
  this_.name = Object.getPrototypeOf(this_).constructor.name;
  if (message == null) {
    var tmp;
    if (isUndefined(message)) {
      var tmp1_elvis_lhs = cause == null ? null : cause.toString();
      tmp = tmp1_elvis_lhs == null ? VOID : tmp1_elvis_lhs;
    } else {
      tmp = VOID;
    }
    this_.message = tmp;
  }
}
function THROW_NPE() {
  throw NullPointerException.i2();
}
function THROW_CCE() {
  throw ClassCastException.m2();
}
function THROW_IAE(msg) {
  throw IllegalArgumentException.q2(msg);
}
function ensureNotNull(v) {
  var tmp;
  if (v == null) {
    THROW_NPE();
  } else {
    tmp = v;
  }
  return tmp;
}
function jsGenerateInterfaceSymbol() {
  return generateInterfaceSymbolById();
}
var longArrayClass_0;
function negate(_this__u8e3s4) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  var tmp = BigInt;
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.internal.unaryMinus' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  var tmp$ret$2 = -_this__u8e3s4;
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return tmp.asIntN(64, tmp$ret$2);
}
function add(_this__u8e3s4, other) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  // Inline function 'kotlin.js.internal.longAsBigInt.wrappingArithmetic' call
  var tmp = BigInt;
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.internal.plus' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  var tmp$ret$5 = _this__u8e3s4 + other;
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return tmp.asIntN(64, tmp$ret$5);
}
function subtract(_this__u8e3s4, other) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  // Inline function 'kotlin.js.internal.longAsBigInt.wrappingArithmetic' call
  var tmp = BigInt;
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.internal.minus' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  var tmp$ret$5 = _this__u8e3s4 - other;
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return tmp.asIntN(64, tmp$ret$5);
}
function multiply(_this__u8e3s4, other) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  // Inline function 'kotlin.js.internal.longAsBigInt.wrappingArithmetic' call
  var tmp = BigInt;
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.internal.times' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  var tmp$ret$5 = _this__u8e3s4 * other;
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return tmp.asIntN(64, tmp$ret$5);
}
function divide(_this__u8e3s4, other) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  // Inline function 'kotlin.js.internal.longAsBigInt.wrappingArithmetic' call
  var tmp = BigInt;
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.internal.div' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  var tmp$ret$5 = _this__u8e3s4 / other;
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return tmp.asIntN(64, tmp$ret$5);
}
function modulo(_this__u8e3s4, other) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  // Inline function 'kotlin.js.internal.longAsBigInt.wrappingArithmetic' call
  var tmp = BigInt;
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.internal.rem' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  var tmp$ret$5 = _this__u8e3s4 % other;
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return tmp.asIntN(64, tmp$ret$5);
}
function shiftLeft(_this__u8e3s4, numBits) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  // Inline function 'kotlin.js.internal.longAsBigInt.wrappingArithmetic' call
  var tmp = BigInt;
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.internal.shl' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  var tmp$ret$5 = _this__u8e3s4 << fromInt_0(sanitizeBitShiftRHS(numBits));
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return tmp.asIntN(64, tmp$ret$5);
}
function sanitizeBitShiftRHS(numBits) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  return numBits & 63;
}
function shiftRight(_this__u8e3s4, numBits) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  // Inline function 'kotlin.js.internal.longAsBigInt.wrappingArithmetic' call
  var tmp = BigInt;
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.internal.shr' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  var tmp$ret$5 = _this__u8e3s4 >> fromInt_0(sanitizeBitShiftRHS(numBits));
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return tmp.asIntN(64, tmp$ret$5);
}
function numberToLong(value) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  var tmp0_elvis_lhs = (!(value == null) ? typeof value === 'bigint' : false) ? value : null;
  var tmp;
  if (tmp0_elvis_lhs == null) {
    // Inline function 'kotlin.js.unsafeCast' call
    tmp = fromNumber(value);
  } else {
    tmp = tmp0_elvis_lhs;
  }
  return tmp;
}
function fromNumber(value) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  var tmp;
  if (isNaN_0(value)) {
    tmp = 0n;
  } else if (value <= -9.223372036854776E18) {
    tmp = -9223372036854775808n;
  } else if (value + 1 >= 9.223372036854776E18) {
    tmp = 9223372036854775807n;
  } else {
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp = BigInt(trunc(value));
  }
  return tmp;
}
function fromInt_0(value) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return BigInt(value);
}
function truncating(_this__u8e3s4, bitSize) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  var tmp = BigInt;
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.internal.toNumber' call
  var self_0 = tmp.asIntN(bitSize, _this__u8e3s4);
  // Inline function 'kotlin.js.unsafeCast' call
  return Number(self_0);
}
function toNumber(_this__u8e3s4) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.internal.toNumber' call
  var self_0 = _this__u8e3s4;
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return Number(self_0);
}
function convertToInt(_this__u8e3s4) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return truncating(_this__u8e3s4, 32);
}
function longFromTwoInts(low, high) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  return shiftLeft(fromInt_0(high), 32) | fromInt_0(low) & 4294967295n;
}
function lowBits(_this__u8e3s4) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  return convertToInt(_this__u8e3s4);
}
function highBits(_this__u8e3s4) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  return convertToInt(shiftRight(_this__u8e3s4, 32));
}
function isLongArray(a) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  // Inline function 'kotlin.js.jsInstanceOf' call
  return a instanceof BigInt64Array;
}
function longArrayClass$lambda_0(it) {
  _init_properties_longAsBigInt_kt__j3nkxv();
  return !(it == null) ? isLongArray(it) : false;
}
var properties_initialized_longAsBigInt_kt_s7aby9;
function _init_properties_longAsBigInt_kt__j3nkxv() {
  if (!properties_initialized_longAsBigInt_kt_s7aby9) {
    properties_initialized_longAsBigInt_kt_s7aby9 = true;
    // Inline function 'kotlin.js.unsafeCast' call
    var tmp = BigInt64Array;
    longArrayClass_0 = new PrimitiveKClassImpl(tmp, 'LongArray', longArrayClass$lambda_0);
  }
}
function createMetadata(kind, name, defaultConstructor, associatedObjectKey, associatedObjects, suspendArity) {
  var undef = VOID;
  return {kind: kind, simpleName: name, associatedObjectKey: associatedObjectKey, associatedObjects: associatedObjects, suspendArity: suspendArity, $kClass$: undef, defaultConstructor: defaultConstructor};
}
function initMetadataForClass(ctor, name, defaultConstructor, parent, interfaces, suspendArity, associatedObjectKey, associatedObjects) {
  var kind = 'class';
  initMetadataFor(kind, ctor, name, defaultConstructor, parent, interfaces, suspendArity, associatedObjectKey, associatedObjects);
}
function initMetadataFor(kind, ctor, name, defaultConstructor, parent, interfaces, suspendArity, associatedObjectKey, associatedObjects) {
  if (!(parent == null)) {
    ctor.prototype = Object.create(parent.prototype);
    ctor.prototype.constructor = ctor;
  }
  var metadata = createMetadata(kind, name, defaultConstructor, associatedObjectKey, associatedObjects, suspendArity);
  ctor.$metadata$ = metadata;
  var prototype = ctor.prototype;
  if (!(interfaces == null)) {
    var inductionVariable = 0;
    var last = interfaces.length;
    while (inductionVariable < last) {
      var i = interfaces[inductionVariable];
      inductionVariable = inductionVariable + 1 | 0;
      Object.assign(prototype, i.prototype);
      prototype[i.Symbol] = true;
    }
  }
  if (kind === 'interface') {
    ctor.Symbol = Symbol();
  }
}
function generateInterfaceSymbolById() {
  return '#__interface_' + generateInterfaceId();
}
function generateInterfaceId() {
  if (globalInterfaceId === VOID) {
    globalInterfaceId = 0;
  }
  // Inline function 'kotlin.js.unsafeCast' call
  globalInterfaceId = globalInterfaceId + 1 | 0;
  return globalInterfaceId;
}
var globalInterfaceId;
function initMetadataForObject(ctor, name, defaultConstructor, parent, interfaces, suspendArity, associatedObjectKey, associatedObjects) {
  var kind = 'object';
  initMetadataFor(kind, ctor, name, defaultConstructor, parent, interfaces, suspendArity, associatedObjectKey, associatedObjects);
}
function initMetadataForInterface(ctor, name, defaultConstructor, parent, interfaces, suspendArity, associatedObjectKey, associatedObjects) {
  var kind = 'interface';
  initMetadataFor(kind, ctor, name, defaultConstructor, parent, interfaces, suspendArity, associatedObjectKey, associatedObjects);
}
function initMetadataForLambda(ctor, parent, interfaces, suspendArity) {
  initMetadataForClass(ctor, 'Lambda', VOID, parent, interfaces, suspendArity, VOID, VOID);
}
function initMetadataForCoroutine(ctor, parent, interfaces, suspendArity) {
  initMetadataForClass(ctor, 'Coroutine', VOID, parent, interfaces, suspendArity, VOID, VOID);
}
function initMetadataForFunctionReference(ctor, parent, interfaces, suspendArity) {
  initMetadataForClass(ctor, 'FunctionReference', VOID, parent, interfaces, suspendArity, VOID, VOID);
}
function initMetadataForCompanion(ctor, parent, interfaces, suspendArity) {
  initMetadataForObject(ctor, 'Companion', VOID, parent, interfaces, suspendArity, VOID, VOID);
}
function toByte(a) {
  // Inline function 'kotlin.js.unsafeCast' call
  return a << 24 >> 24;
}
function numberToInt(a) {
  var tmp;
  if (!(a == null) ? typeof a === 'bigint' : false) {
    tmp = convertToInt(a);
  } else {
    tmp = doubleToInt(a);
  }
  return tmp;
}
function doubleToInt(a) {
  var tmp;
  if (a > 2147483647) {
    tmp = 2147483647;
  } else if (a < -2147483648) {
    tmp = -2147483648;
  } else {
    // Inline function 'kotlin.js.jsBitwiseOr' call
    tmp = a | 0;
  }
  return tmp;
}
function toShort(a) {
  // Inline function 'kotlin.js.unsafeCast' call
  return a << 16 >> 16;
}
function numberToChar(a) {
  // Inline function 'kotlin.toUShort' call
  var this_0 = numberToInt(a);
  var tmp$ret$0 = _UShort___init__impl__jigrne(toShort(this_0));
  return _Char___init__impl__6a9atx_0(tmp$ret$0);
}
var StringCompanionObject_instance;
function StringCompanionObject_getInstance() {
  return StringCompanionObject_instance;
}
function numberRangeToNumber(start, endInclusive) {
  return new IntRange(start, endInclusive);
}
function get_propertyRefClassMetadataCache() {
  _init_properties_reflectRuntime_kt__5r4uu3();
  return propertyRefClassMetadataCache;
}
var propertyRefClassMetadataCache;
function metadataObject() {
  _init_properties_reflectRuntime_kt__5r4uu3();
  return createMetadata('class', VOID, VOID, VOID, VOID, VOID);
}
function getPropertyCallableRef(name, paramCount, superType, getter, setter, linkageError) {
  _init_properties_reflectRuntime_kt__5r4uu3();
  getter.get = getter;
  getter.set = setter;
  getter.callableName = name;
  // Inline function 'kotlin.js.unsafeCast' call
  return getPropertyRefClass(getter, getKPropMetadata(paramCount, setter), superType);
}
function getPropertyRefClass(obj, metadata, superType) {
  _init_properties_reflectRuntime_kt__5r4uu3();
  obj.$metadata$ = metadata;
  obj.constructor = obj;
  var symbol = superType.Symbol;
  if (symbol != null) {
    // Inline function 'kotlin.js.asDynamic' call
    obj[symbol] = true;
  }
  Object.assign(obj, superType.prototype);
  return obj;
}
function getKPropMetadata(paramCount, setter) {
  _init_properties_reflectRuntime_kt__5r4uu3();
  return get_propertyRefClassMetadataCache()[paramCount][setter == null ? 0 : 1];
}
function constructCallableReference(callable, arity, flags, signatureId, name, bounds) {
  _init_properties_reflectRuntime_kt__5r4uu3();
  callable.callableName = name;
  callable.$flags = flags;
  callable.$arity = arity;
  callable.$id = signatureId;
  callable.$bound = bounds;
  return callable;
}
var properties_initialized_reflectRuntime_kt_inkhwd;
function _init_properties_reflectRuntime_kt__5r4uu3() {
  if (!properties_initialized_reflectRuntime_kt_inkhwd) {
    properties_initialized_reflectRuntime_kt_inkhwd = true;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp = [metadataObject(), metadataObject()];
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    var tmp_0 = [metadataObject(), metadataObject()];
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    propertyRefClassMetadataCache = [tmp, tmp_0, [metadataObject(), metadataObject()]];
  }
}
function isArrayish(o) {
  return isJsArray(o) || isView(o);
}
function isJsArray(obj) {
  // Inline function 'kotlin.js.unsafeCast' call
  return Array.isArray(obj);
}
function isCallableReference(value) {
  return typeof value === 'function' && value.$flags != null && value.$arity != null;
}
function isInterface(obj, iface) {
  return obj[iface.Symbol] === true;
}
function isArray(obj) {
  var tmp;
  if (isJsArray(obj)) {
    // Inline function 'kotlin.js.asDynamic' call
    tmp = !obj.$type$;
  } else {
    tmp = false;
  }
  return tmp;
}
function isNumber(a) {
  var tmp;
  if (typeof a === 'number') {
    tmp = true;
  } else {
    tmp = !(a == null) ? typeof a === 'bigint' : false;
  }
  return tmp;
}
function isCharSequence(value) {
  return typeof value === 'string' || isInterface(value, CharSequence);
}
function isBooleanArray(a) {
  return isJsArray(a) && a.$type$ === 'BooleanArray';
}
function isByteArray(a) {
  // Inline function 'kotlin.js.jsInstanceOf' call
  return a instanceof Int8Array;
}
function isShortArray(a) {
  // Inline function 'kotlin.js.jsInstanceOf' call
  return a instanceof Int16Array;
}
function isCharArray(a) {
  var tmp;
  // Inline function 'kotlin.js.jsInstanceOf' call
  if (a instanceof Uint16Array) {
    tmp = a.$type$ === 'CharArray';
  } else {
    tmp = false;
  }
  return tmp;
}
function isIntArray(a) {
  // Inline function 'kotlin.js.jsInstanceOf' call
  return a instanceof Int32Array;
}
function isFloatArray(a) {
  // Inline function 'kotlin.js.jsInstanceOf' call
  return a instanceof Float32Array;
}
function isDoubleArray(a) {
  // Inline function 'kotlin.js.jsInstanceOf' call
  return a instanceof Float64Array;
}
function get_VOID() {
  _init_properties_void_kt__3zg9as();
  return VOID;
}
var VOID;
var properties_initialized_void_kt_e4ret2;
function _init_properties_void_kt__3zg9as() {
  if (!properties_initialized_void_kt_e4ret2) {
    properties_initialized_void_kt_e4ret2 = true;
    VOID = void 0;
  }
}
function copyOf(_this__u8e3s4, newSize) {
  // Inline function 'kotlin.require' call
  if (!(newSize >= 0)) {
    var message = 'Invalid new array size: ' + newSize + '.';
    throw IllegalArgumentException.q2(toString_1(message));
  }
  var size = _this__u8e3s4.length;
  var tmp;
  if (newSize < 16 || size < 16) {
    tmp = fillFrom(_this__u8e3s4, new Int32Array(newSize));
  } else if (newSize > size) {
    // Inline function 'kotlin.also' call
    var this_0 = new Int32Array(newSize);
    // Inline function 'kotlin.js.asDynamic' call
    this_0.set(_this__u8e3s4);
    tmp = this_0;
  } else {
    // Inline function 'kotlin.js.asDynamic' call
    tmp = _this__u8e3s4.slice(0, newSize);
  }
  return tmp;
}
function copyOf_0(_this__u8e3s4, newSize) {
  // Inline function 'kotlin.require' call
  if (!(newSize >= 0)) {
    var message = 'Invalid new array size: ' + newSize + '.';
    throw IllegalArgumentException.q2(toString_1(message));
  }
  return arrayCopyResize(_this__u8e3s4, newSize, null);
}
function asList(_this__u8e3s4) {
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return ArrayList.v1(_this__u8e3s4);
}
function contentEquals(_this__u8e3s4, other) {
  return contentEqualsInternal(_this__u8e3s4, other);
}
function contentHashCode(_this__u8e3s4) {
  return contentHashCodeInternal(_this__u8e3s4);
}
function isWhitespaceImpl(_this__u8e3s4) {
  // Inline function 'kotlin.code' call
  var ch = Char__toInt_impl_vasixd(_this__u8e3s4);
  return (9 <= ch ? ch <= 13 : false) || (28 <= ch ? ch <= 32 : false) || ch === 160 || (ch > 4096 && (ch === 5760 || (8192 <= ch ? ch <= 8202 : false) || ch === 8232 || ch === 8233 || ch === 8239 || ch === 8287 || ch === 12288));
}
function isNaN_0(_this__u8e3s4) {
  return !(_this__u8e3s4 === _this__u8e3s4);
}
function takeHighestOneBit(_this__u8e3s4) {
  var tmp;
  if (_this__u8e3s4 === 0) {
    tmp = 0;
  } else {
    // Inline function 'kotlin.countLeadingZeroBits' call
    tmp = 1 << (31 - clz32(_this__u8e3s4) | 0);
  }
  return tmp;
}
var Unit_instance;
function Unit_getInstance() {
  return Unit_instance;
}
function ulongCompare(v1, v2) {
  return compareTo(v1 ^ -9223372036854775808n, v2 ^ -9223372036854775808n);
}
function copyToArray(collection) {
  var tmp;
  // Inline function 'kotlin.js.asDynamic' call
  if (collection.toArray !== undefined) {
    // Inline function 'kotlin.js.asDynamic' call
    // Inline function 'kotlin.js.unsafeCast' call
    tmp = collection.toArray();
  } else {
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp = collectionToArray(collection);
  }
  return tmp;
}
function checkIndexOverflow(index) {
  if (index < 0) {
    throwIndexOverflow();
  }
  return index;
}
function collectionToArray(collection) {
  return collectionToArrayCommonImpl(collection);
}
function listOf(element) {
  // Inline function 'kotlin.arrayOf' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  var tmp$ret$0 = [element];
  return ArrayList.v1(tmp$ret$0);
}
function mapCapacity(expectedSize) {
  return expectedSize;
}
function mapOf(pair) {
  return hashMapOf([pair]);
}
function arrayOfUninitializedElements(capacity) {
  // Inline function 'kotlin.require' call
  if (!(capacity >= 0)) {
    var message = 'capacity must be non-negative.';
    throw IllegalArgumentException.q2(toString_1(message));
  }
  // Inline function 'kotlin.arrayOfNulls' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return Array(capacity);
}
function resetRange(_this__u8e3s4, fromIndex, toIndex) {
  // Inline function 'kotlin.js.nativeFill' call
  // Inline function 'kotlin.js.asDynamic' call
  _this__u8e3s4.fill(null, fromIndex, toIndex);
}
function copyOfUninitializedElements(_this__u8e3s4, newSize) {
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return copyOf_0(_this__u8e3s4, newSize);
}
var Companion_instance_1;
function Companion_getInstance_1() {
  if (Companion_instance_1 === VOID)
    new Companion_1();
  return Companion_instance_1;
}
function increaseLength($this, amount) {
  var previous = $this.i();
  // Inline function 'kotlin.js.asDynamic' call
  $this.k_1.length = $this.i() + amount | 0;
  return previous;
}
function rangeCheck($this, index) {
  // Inline function 'kotlin.apply' call
  Companion_instance_3.q3(index, $this.i());
  return index;
}
function insertionRangeCheck($this, index) {
  // Inline function 'kotlin.apply' call
  Companion_instance_3.d3(index, $this.i());
  return index;
}
function init_kotlin_collections_HashMap(_this__u8e3s4) {
  _this__u8e3s4.r4_1 = null;
}
function init_kotlin_collections_HashSet(_this__u8e3s4) {
}
function computeHashSize($this, capacity) {
  return takeHighestOneBit(imul_0(coerceAtLeast(capacity, 1), 3));
}
function computeShift($this, hashSize) {
  // Inline function 'kotlin.countLeadingZeroBits' call
  return clz32(hashSize) + 1 | 0;
}
function checkForComodification($this) {
  if (!($this.f6_1.b5_1 === $this.h6_1))
    throw ConcurrentModificationException.e6('The backing map has been modified after this entry was obtained.');
}
function _get_capacity__a9k9f3($this) {
  return $this.u4_1.length;
}
function _get_hashSize__tftcho($this) {
  return $this.x4_1.length;
}
function registerModification($this) {
  $this.b5_1 = $this.b5_1 + 1 | 0;
}
function ensureExtraCapacity($this, n) {
  if (shouldCompact($this, n)) {
    compact($this, true);
  } else {
    ensureCapacity($this, $this.z4_1 + n | 0);
  }
}
function shouldCompact($this, extraCapacity) {
  var spareCapacity = _get_capacity__a9k9f3($this) - $this.z4_1 | 0;
  var gaps = $this.z4_1 - $this.i() | 0;
  return spareCapacity < extraCapacity && (gaps + spareCapacity | 0) >= extraCapacity && gaps >= (_get_capacity__a9k9f3($this) / 4 | 0);
}
function ensureCapacity($this, minCapacity) {
  if (minCapacity < 0)
    throw RuntimeException.k6('too many elements');
  if (minCapacity > _get_capacity__a9k9f3($this)) {
    var newSize = Companion_instance_3.l6(_get_capacity__a9k9f3($this), minCapacity);
    $this.u4_1 = copyOfUninitializedElements($this.u4_1, newSize);
    var tmp = $this;
    var tmp0_safe_receiver = $this.v4_1;
    tmp.v4_1 = tmp0_safe_receiver == null ? null : copyOfUninitializedElements(tmp0_safe_receiver, newSize);
    $this.w4_1 = copyOf($this.w4_1, newSize);
    var newHashSize = computeHashSize(Companion_instance_2, newSize);
    if (newHashSize > _get_hashSize__tftcho($this)) {
      rehash($this, newHashSize);
    }
  }
}
function allocateValuesArray($this) {
  var curValuesArray = $this.v4_1;
  if (!(curValuesArray == null))
    return curValuesArray;
  var newValuesArray = arrayOfUninitializedElements(_get_capacity__a9k9f3($this));
  $this.v4_1 = newValuesArray;
  return newValuesArray;
}
function hash($this, key) {
  return key == null ? 0 : imul_0(hashCode(key), -1640531527) >>> $this.a5_1 | 0;
}
function compact($this, updateHashArray) {
  var i = 0;
  var j = 0;
  var valuesArray = $this.v4_1;
  while (i < $this.z4_1) {
    var hash = $this.w4_1[i];
    if (hash >= 0) {
      $this.u4_1[j] = $this.u4_1[i];
      if (!(valuesArray == null)) {
        valuesArray[j] = valuesArray[i];
      }
      if (updateHashArray) {
        $this.w4_1[j] = hash;
        $this.x4_1[hash] = j + 1 | 0;
      }
      j = j + 1 | 0;
    }
    i = i + 1 | 0;
  }
  resetRange($this.u4_1, j, $this.z4_1);
  if (valuesArray == null)
    null;
  else {
    resetRange(valuesArray, j, $this.z4_1);
  }
  $this.z4_1 = j;
}
function rehash($this, newHashSize) {
  registerModification($this);
  if ($this.z4_1 > $this.c5_1) {
    compact($this, false);
  }
  $this.x4_1 = new Int32Array(newHashSize);
  $this.a5_1 = computeShift(Companion_instance_2, newHashSize);
  var i = 0;
  while (i < $this.z4_1) {
    var _unary__edvuaz = i;
    i = _unary__edvuaz + 1 | 0;
    if (!putRehash($this, _unary__edvuaz)) {
      throw IllegalStateException.e2('This cannot happen with fixed magic multiplier and grow-only hash array. Have object hashCodes changed?');
    }
  }
}
function putRehash($this, i) {
  var hash_0 = hash($this, $this.u4_1[i]);
  var probesLeft = $this.y4_1;
  while (true) {
    var index = $this.x4_1[hash_0];
    if (index === 0) {
      $this.x4_1[hash_0] = i + 1 | 0;
      $this.w4_1[i] = hash_0;
      return true;
    }
    probesLeft = probesLeft - 1 | 0;
    if (probesLeft < 0)
      return false;
    var _unary__edvuaz = hash_0;
    hash_0 = _unary__edvuaz - 1 | 0;
    if (_unary__edvuaz === 0)
      hash_0 = _get_hashSize__tftcho($this) - 1 | 0;
  }
}
function findKey($this, key) {
  var hash_0 = hash($this, key);
  var probesLeft = $this.y4_1;
  while (true) {
    var index = $this.x4_1[hash_0];
    if (index === 0)
      return -1;
    if (equals($this.u4_1[index - 1 | 0], key))
      return index - 1 | 0;
    probesLeft = probesLeft - 1 | 0;
    if (probesLeft < 0)
      return -1;
    var _unary__edvuaz = hash_0;
    hash_0 = _unary__edvuaz - 1 | 0;
    if (_unary__edvuaz === 0)
      hash_0 = _get_hashSize__tftcho($this) - 1 | 0;
  }
}
function addKey($this, key) {
  $this.m6();
  retry: while (true) {
    var hash_0 = hash($this, key);
    var tentativeMaxProbeDistance = coerceAtMost(imul_0($this.y4_1, 2), _get_hashSize__tftcho($this) / 2 | 0);
    var probeDistance = 0;
    while (true) {
      var index = $this.x4_1[hash_0];
      if (index === 0) {
        if ($this.z4_1 >= _get_capacity__a9k9f3($this)) {
          ensureExtraCapacity($this, 1);
          continue retry;
        }
        var _unary__edvuaz = $this.z4_1;
        $this.z4_1 = _unary__edvuaz + 1 | 0;
        var putIndex = _unary__edvuaz;
        $this.u4_1[putIndex] = key;
        $this.w4_1[putIndex] = hash_0;
        $this.x4_1[hash_0] = putIndex + 1 | 0;
        $this.c5_1 = $this.c5_1 + 1 | 0;
        registerModification($this);
        if (probeDistance > $this.y4_1)
          $this.y4_1 = probeDistance;
        return putIndex;
      }
      if (equals($this.u4_1[index - 1 | 0], key)) {
        return -index | 0;
      }
      probeDistance = probeDistance + 1 | 0;
      if (probeDistance > tentativeMaxProbeDistance) {
        rehash($this, imul_0(_get_hashSize__tftcho($this), 2));
        continue retry;
      }
      var _unary__edvuaz_0 = hash_0;
      hash_0 = _unary__edvuaz_0 - 1 | 0;
      if (_unary__edvuaz_0 === 0)
        hash_0 = _get_hashSize__tftcho($this) - 1 | 0;
    }
  }
}
function contentEquals_0($this, other) {
  return $this.c5_1 === other.i() && $this.u5(other.h1());
}
var Companion_instance_2;
function Companion_getInstance_2() {
  return Companion_instance_2;
}
function init_kotlin_collections_LinkedHashMap(_this__u8e3s4) {
}
function init_kotlin_UnsupportedOperationException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.z1_1);
}
function init_kotlin_IllegalArgumentException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.p2_1);
}
function init_kotlin_RuntimeException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.j6_1);
}
function init_kotlin_Exception(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.d8_1);
}
function init_kotlin_NoSuchElementException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.t1_1);
}
function init_kotlin_IndexOutOfBoundsException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.j8_1);
}
function init_kotlin_IllegalStateException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.d2_1);
}
function init_kotlin_ConcurrentModificationException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.d6_1);
}
function init_kotlin_ArithmeticException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.p8_1);
}
function init_kotlin_NullPointerException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.h2_1);
}
function init_kotlin_ClassCastException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.l2_1);
}
function fillFrom(src, dst) {
  var srcLen = src.length;
  var dstLen = dst.length;
  var index = 0;
  // Inline function 'kotlin.js.unsafeCast' call
  var arr = dst;
  while (index < srcLen && index < dstLen) {
    var tmp = index;
    var _unary__edvuaz = index;
    index = _unary__edvuaz + 1 | 0;
    arr[tmp] = src[_unary__edvuaz];
  }
  return dst;
}
function arrayCopyResize(source, newSize, defaultValue) {
  // Inline function 'kotlin.js.unsafeCast' call
  var result = source.slice(0, newSize);
  // Inline function 'kotlin.copyArrayType' call
  if (source.$type$ !== undefined) {
    result.$type$ = source.$type$;
  }
  var index = source.length;
  if (newSize > index) {
    // Inline function 'kotlin.js.asDynamic' call
    result.length = newSize;
    while (index < newSize) {
      var _unary__edvuaz = index;
      index = _unary__edvuaz + 1 | 0;
      result[_unary__edvuaz] = defaultValue;
    }
  }
  return result;
}
function lazy(mode, initializer) {
  return new UnsafeLazyImpl(initializer);
}
function lazy_0(initializer) {
  return new UnsafeLazyImpl(initializer);
}
function abs_0(n) {
  return n < 0 ? -n | 0 : n;
}
var NothingKClassImpl_instance;
function NothingKClassImpl_getInstance() {
  if (NothingKClassImpl_instance === VOID)
    new NothingKClassImpl();
  return NothingKClassImpl_instance;
}
function get_functionClasses() {
  _init_properties_primitives_kt__3fums4();
  return functionClasses;
}
var functionClasses;
function PrimitiveClasses$anyClass$lambda(it) {
  return !(it == null);
}
function PrimitiveClasses$numberClass$lambda(it) {
  return isNumber(it);
}
function PrimitiveClasses$booleanClass$lambda(it) {
  return !(it == null) ? typeof it === 'boolean' : false;
}
function PrimitiveClasses$byteClass$lambda(it) {
  return !(it == null) ? typeof it === 'number' : false;
}
function PrimitiveClasses$shortClass$lambda(it) {
  return !(it == null) ? typeof it === 'number' : false;
}
function PrimitiveClasses$intClass$lambda(it) {
  return !(it == null) ? typeof it === 'number' : false;
}
function PrimitiveClasses$longClass$lambda(it) {
  return !(it == null) ? typeof it === 'bigint' : false;
}
function PrimitiveClasses$floatClass$lambda(it) {
  return !(it == null) ? typeof it === 'number' : false;
}
function PrimitiveClasses$doubleClass$lambda(it) {
  return !(it == null) ? typeof it === 'number' : false;
}
function PrimitiveClasses$arrayClass$lambda(it) {
  return !(it == null) ? isArray(it) : false;
}
function PrimitiveClasses$stringClass$lambda(it) {
  return !(it == null) ? typeof it === 'string' : false;
}
function PrimitiveClasses$throwableClass$lambda(it) {
  return it instanceof Error;
}
function PrimitiveClasses$booleanArrayClass$lambda(it) {
  return !(it == null) ? isBooleanArray(it) : false;
}
function PrimitiveClasses$charArrayClass$lambda(it) {
  return !(it == null) ? isCharArray(it) : false;
}
function PrimitiveClasses$byteArrayClass$lambda(it) {
  return !(it == null) ? isByteArray(it) : false;
}
function PrimitiveClasses$shortArrayClass$lambda(it) {
  return !(it == null) ? isShortArray(it) : false;
}
function PrimitiveClasses$intArrayClass$lambda(it) {
  return !(it == null) ? isIntArray(it) : false;
}
function PrimitiveClasses$bigintClass$lambda(it) {
  return typeof it === 'bigint';
}
function PrimitiveClasses$longArrayClass$lambda(it) {
  return !(it == null) ? isLongArray(it) : false;
}
function PrimitiveClasses$floatArrayClass$lambda(it) {
  return !(it == null) ? isFloatArray(it) : false;
}
function PrimitiveClasses$doubleArrayClass$lambda(it) {
  return !(it == null) ? isDoubleArray(it) : false;
}
function PrimitiveClasses$functionClass$lambda($arity) {
  return (it) => {
    var tmp;
    if (typeof it === 'function') {
      // Inline function 'kotlin.js.asDynamic' call
      tmp = it.length === $arity;
    } else {
      tmp = false;
    }
    return tmp;
  };
}
var PrimitiveClasses_instance;
function PrimitiveClasses_getInstance() {
  if (PrimitiveClasses_instance === VOID)
    new PrimitiveClasses();
  return PrimitiveClasses_instance;
}
var properties_initialized_primitives_kt_jle18u;
function _init_properties_primitives_kt__3fums4() {
  if (!properties_initialized_primitives_kt_jle18u) {
    properties_initialized_primitives_kt_jle18u = true;
    // Inline function 'kotlin.arrayOfNulls' call
    functionClasses = Array(0);
  }
}
function getKClass(jClass) {
  if (jClass === String) {
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return PrimitiveClasses_getInstance().stringClass;
  }
  // Inline function 'kotlin.js.asDynamic' call
  var metadata = jClass.$metadata$;
  var tmp;
  if (metadata != null) {
    var tmp_0;
    if (metadata.$kClass$ == null) {
      var kClass = new SimpleKClassImpl(jClass);
      metadata.$kClass$ = kClass;
      tmp_0 = kClass;
    } else {
      tmp_0 = metadata.$kClass$;
    }
    tmp = tmp_0;
  } else {
    tmp = new SimpleKClassImpl(jClass);
  }
  return tmp;
}
function getKClassFromExpression(e) {
  var tmp;
  switch (typeof e) {
    case 'string':
      tmp = PrimitiveClasses_getInstance().stringClass;
      break;
    case 'number':
      var tmp_0;
      // Inline function 'kotlin.js.jsBitwiseOr' call

      // Inline function 'kotlin.js.asDynamic' call

      if ((e | 0) === e) {
        tmp_0 = PrimitiveClasses_getInstance().intClass;
      } else {
        tmp_0 = PrimitiveClasses_getInstance().doubleClass;
      }

      tmp = tmp_0;
      break;
    case 'bigint':
      tmp = true && BigInt.asIntN(64, e) === e ? PrimitiveClasses_getInstance().longClass : PrimitiveClasses_getInstance().bigIntClass;
      break;
    case 'boolean':
      tmp = PrimitiveClasses_getInstance().booleanClass;
      break;
    case 'function':
      var tmp_1 = PrimitiveClasses_getInstance();
      // Inline function 'kotlin.js.asDynamic' call

      tmp = tmp_1.functionClass(e.length);
      break;
    default:
      var tmp_2;
      if (isBooleanArray(e)) {
        tmp_2 = PrimitiveClasses_getInstance().booleanArrayClass;
      } else {
        if (isCharArray(e)) {
          tmp_2 = PrimitiveClasses_getInstance().charArrayClass;
        } else {
          if (isByteArray(e)) {
            tmp_2 = PrimitiveClasses_getInstance().byteArrayClass;
          } else {
            if (isShortArray(e)) {
              tmp_2 = PrimitiveClasses_getInstance().shortArrayClass;
            } else {
              if (isIntArray(e)) {
                tmp_2 = PrimitiveClasses_getInstance().intArrayClass;
              } else {
                if (isLongArray(e)) {
                  tmp_2 = PrimitiveClasses_getInstance().t9();
                } else {
                  if (isFloatArray(e)) {
                    tmp_2 = PrimitiveClasses_getInstance().floatArrayClass;
                  } else {
                    if (isDoubleArray(e)) {
                      tmp_2 = PrimitiveClasses_getInstance().doubleArrayClass;
                    } else {
                      if (isInterface(e, KClass)) {
                        tmp_2 = getKClass(KClass);
                      } else {
                        if (isArray(e)) {
                          tmp_2 = PrimitiveClasses_getInstance().arrayClass;
                        } else {
                          var constructor = Object.getPrototypeOf(e).constructor;
                          var tmp_3;
                          if (constructor === Object) {
                            tmp_3 = PrimitiveClasses_getInstance().anyClass;
                          } else if (constructor === Error) {
                            tmp_3 = PrimitiveClasses_getInstance().throwableClass;
                          } else {
                            var jsClass = constructor;
                            tmp_3 = getKClass(jsClass);
                          }
                          tmp_2 = tmp_3;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }

      tmp = tmp_2;
      break;
  }
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return tmp;
}
function uppercaseChar(_this__u8e3s4) {
  // Inline function 'kotlin.text.uppercase' call
  // Inline function 'kotlin.js.asDynamic' call
  // Inline function 'kotlin.js.unsafeCast' call
  var uppercase = toString(_this__u8e3s4).toUpperCase();
  return uppercase.length > 1 ? _this__u8e3s4 : charCodeAt(uppercase, 0);
}
function isWhitespace(_this__u8e3s4) {
  return isWhitespaceImpl(_this__u8e3s4);
}
var STRING_CASE_INSENSITIVE_ORDER;
function substring(_this__u8e3s4, startIndex, endIndex) {
  _init_properties_stringJs_kt__bg7zye();
  // Inline function 'kotlin.js.asDynamic' call
  return _this__u8e3s4.substring(startIndex, endIndex);
}
function substring_0(_this__u8e3s4, startIndex) {
  _init_properties_stringJs_kt__bg7zye();
  // Inline function 'kotlin.js.asDynamic' call
  return _this__u8e3s4.substring(startIndex);
}
function compareTo_0(_this__u8e3s4, other, ignoreCase) {
  ignoreCase = ignoreCase === VOID ? false : ignoreCase;
  _init_properties_stringJs_kt__bg7zye();
  if (ignoreCase) {
    var n1 = _this__u8e3s4.length;
    var n2 = other.length;
    // Inline function 'kotlin.comparisons.minOf' call
    var min = Math.min(n1, n2);
    if (min === 0)
      return n1 - n2 | 0;
    var inductionVariable = 0;
    if (inductionVariable < min)
      do {
        var index = inductionVariable;
        inductionVariable = inductionVariable + 1 | 0;
        var thisChar = charCodeAt(_this__u8e3s4, index);
        var otherChar = charCodeAt(other, index);
        if (!(thisChar === otherChar)) {
          thisChar = uppercaseChar(thisChar);
          otherChar = uppercaseChar(otherChar);
          if (!(thisChar === otherChar)) {
            // Inline function 'kotlin.text.lowercaseChar' call
            // Inline function 'kotlin.text.lowercase' call
            var this_0 = thisChar;
            // Inline function 'kotlin.js.asDynamic' call
            // Inline function 'kotlin.js.unsafeCast' call
            var tmp$ret$2 = toString(this_0).toLowerCase();
            thisChar = charCodeAt(tmp$ret$2, 0);
            // Inline function 'kotlin.text.lowercaseChar' call
            // Inline function 'kotlin.text.lowercase' call
            var this_1 = otherChar;
            // Inline function 'kotlin.js.asDynamic' call
            // Inline function 'kotlin.js.unsafeCast' call
            var tmp$ret$6 = toString(this_1).toLowerCase();
            otherChar = charCodeAt(tmp$ret$6, 0);
            if (!(thisChar === otherChar)) {
              return Char__compareTo_impl_ypi4mb(thisChar, otherChar);
            }
          }
        }
      }
       while (inductionVariable < min);
    return n1 - n2 | 0;
  } else {
    return compareTo(_this__u8e3s4, other);
  }
}
function decodeToString(_this__u8e3s4) {
  _init_properties_stringJs_kt__bg7zye();
  return decodeUtf8(_this__u8e3s4, 0, _this__u8e3s4.length, false);
}
function STRING_CASE_INSENSITIVE_ORDER$lambda(a, b) {
  _init_properties_stringJs_kt__bg7zye();
  return compareTo_0(a, b, true);
}
var properties_initialized_stringJs_kt_nta8o4;
function _init_properties_stringJs_kt__bg7zye() {
  if (!properties_initialized_stringJs_kt_nta8o4) {
    properties_initialized_stringJs_kt_nta8o4 = true;
    var tmp = STRING_CASE_INSENSITIVE_ORDER$lambda;
    STRING_CASE_INSENSITIVE_ORDER = new sam$kotlin_Comparator$0(tmp);
  }
}
var REPLACEMENT_BYTE_SEQUENCE;
function decodeUtf8(bytes, startIndex, endIndex, throwOnMalformed) {
  _init_properties_utf8Encoding_kt__9thjs4();
  // Inline function 'kotlin.require' call
  // Inline function 'kotlin.require' call
  if (!(startIndex >= 0 && endIndex <= bytes.length && startIndex <= endIndex)) {
    var message = 'Failed requirement.';
    throw IllegalArgumentException.q2(toString_1(message));
  }
  var byteIndex = startIndex;
  var stringBuilder = StringBuilder.d();
  while (byteIndex < endIndex) {
    var _unary__edvuaz = byteIndex;
    byteIndex = _unary__edvuaz + 1 | 0;
    var byte = bytes[_unary__edvuaz];
    if (byte >= 0)
      stringBuilder.k7(numberToChar(byte));
    else if (byte >> 5 === -2) {
      var code = codePointFrom2(bytes, byte, byteIndex, endIndex, throwOnMalformed);
      if (code <= 0) {
        stringBuilder.k7(_Char___init__impl__6a9atx(65533));
        byteIndex = byteIndex + (-code | 0) | 0;
      } else {
        stringBuilder.k7(numberToChar(code));
        byteIndex = byteIndex + 1 | 0;
      }
    } else if (byte >> 4 === -2) {
      var code_0 = codePointFrom3(bytes, byte, byteIndex, endIndex, throwOnMalformed);
      if (code_0 <= 0) {
        stringBuilder.k7(_Char___init__impl__6a9atx(65533));
        byteIndex = byteIndex + (-code_0 | 0) | 0;
      } else {
        stringBuilder.k7(numberToChar(code_0));
        byteIndex = byteIndex + 2 | 0;
      }
    } else if (byte >> 3 === -2) {
      var code_1 = codePointFrom4(bytes, byte, byteIndex, endIndex, throwOnMalformed);
      if (code_1 <= 0) {
        stringBuilder.k7(_Char___init__impl__6a9atx(65533));
        byteIndex = byteIndex + (-code_1 | 0) | 0;
      } else {
        var high = (code_1 - 65536 | 0) >> 10 | 55296;
        var low = code_1 & 1023 | 56320;
        stringBuilder.k7(numberToChar(high));
        stringBuilder.k7(numberToChar(low));
        byteIndex = byteIndex + 3 | 0;
      }
    } else {
      malformed(0, byteIndex, throwOnMalformed);
      stringBuilder.k7(_Char___init__impl__6a9atx(65533));
    }
  }
  return stringBuilder.toString();
}
function codePointFrom2(bytes, byte1, index, endIndex, throwOnMalformed) {
  _init_properties_utf8Encoding_kt__9thjs4();
  if ((byte1 & 30) === 0 || index >= endIndex) {
    return malformed(0, index, throwOnMalformed);
  }
  var byte2 = bytes[index];
  if (!((byte2 & 192) === 128)) {
    return malformed(0, index, throwOnMalformed);
  }
  return byte1 << 6 ^ byte2 ^ 3968;
}
function codePointFrom3(bytes, byte1, index, endIndex, throwOnMalformed) {
  _init_properties_utf8Encoding_kt__9thjs4();
  if (index >= endIndex) {
    return malformed(0, index, throwOnMalformed);
  }
  var byte2 = bytes[index];
  if ((byte1 & 15) === 0) {
    if (!((byte2 & 224) === 160)) {
      return malformed(0, index, throwOnMalformed);
    }
  } else if ((byte1 & 15) === 13) {
    if (!((byte2 & 224) === 128)) {
      return malformed(0, index, throwOnMalformed);
    }
  } else if (!((byte2 & 192) === 128)) {
    return malformed(0, index, throwOnMalformed);
  }
  if ((index + 1 | 0) === endIndex) {
    return malformed(1, index, throwOnMalformed);
  }
  var byte3 = bytes[index + 1 | 0];
  if (!((byte3 & 192) === 128)) {
    return malformed(1, index, throwOnMalformed);
  }
  return byte1 << 12 ^ byte2 << 6 ^ byte3 ^ -123008;
}
function codePointFrom4(bytes, byte1, index, endIndex, throwOnMalformed) {
  _init_properties_utf8Encoding_kt__9thjs4();
  if (index >= endIndex) {
    return malformed(0, index, throwOnMalformed);
  }
  var byte2 = bytes[index];
  if ((byte1 & 15) === 0) {
    if ((byte2 & 240) <= 128) {
      return malformed(0, index, throwOnMalformed);
    }
  } else if ((byte1 & 15) === 4) {
    if (!((byte2 & 240) === 128)) {
      return malformed(0, index, throwOnMalformed);
    }
  } else if ((byte1 & 15) > 4) {
    return malformed(0, index, throwOnMalformed);
  }
  if (!((byte2 & 192) === 128)) {
    return malformed(0, index, throwOnMalformed);
  }
  if ((index + 1 | 0) === endIndex) {
    return malformed(1, index, throwOnMalformed);
  }
  var byte3 = bytes[index + 1 | 0];
  if (!((byte3 & 192) === 128)) {
    return malformed(1, index, throwOnMalformed);
  }
  if ((index + 2 | 0) === endIndex) {
    return malformed(2, index, throwOnMalformed);
  }
  var byte4 = bytes[index + 2 | 0];
  if (!((byte4 & 192) === 128)) {
    return malformed(2, index, throwOnMalformed);
  }
  return byte1 << 18 ^ byte2 << 12 ^ byte3 << 6 ^ byte4 ^ 3678080;
}
function malformed(size, index, throwOnMalformed) {
  _init_properties_utf8Encoding_kt__9thjs4();
  if (throwOnMalformed)
    throw CharacterCodingException.y9('Malformed sequence starting at ' + (index - 1 | 0));
  return -size | 0;
}
var properties_initialized_utf8Encoding_kt_eee1vq;
function _init_properties_utf8Encoding_kt__9thjs4() {
  if (!properties_initialized_utf8Encoding_kt_eee1vq) {
    properties_initialized_utf8Encoding_kt_eee1vq = true;
    // Inline function 'kotlin.byteArrayOf' call
    REPLACEMENT_BYTE_SEQUENCE = new Int8Array([-17, -65, -67]);
  }
}
function systemClockNow() {
  return Companion_getInstance_8().ha(numberToLong((new Date()).getTime()));
}
function formatBytesInto(_this__u8e3s4, dst, dstOffset, startIndex, endIndex) {
  var dstIndex = dstOffset;
  if (startIndex < 4) {
    dstIndex = formatBytesInto_0(highBits(_this__u8e3s4), dst, dstIndex, startIndex, coerceAtMost(endIndex, 4));
  }
  if (endIndex > 4) {
    formatBytesInto_0(lowBits(_this__u8e3s4), dst, dstIndex, coerceAtLeast(startIndex - 4 | 0, 0), endIndex - 4 | 0);
  }
}
function getLongAt(_this__u8e3s4, index) {
  var tmp0_high = getIntAt(_this__u8e3s4, index);
  var tmp1_low = getIntAt(_this__u8e3s4, index + 4 | 0);
  return longFromTwoInts(tmp1_low, tmp0_high);
}
function secureRandomBytes(destination) {
  crypto.getRandomValues(destination);
}
function formatBytesInto_0(_this__u8e3s4, dst, dstOffset, startIndex, endIndex) {
  var dstIndex = dstOffset;
  var inductionVariable = 3 - startIndex | 0;
  var last = 4 - endIndex | 0;
  if (last <= inductionVariable)
    do {
      var reversedIndex = inductionVariable;
      inductionVariable = inductionVariable + -1 | 0;
      var shift = reversedIndex << 3;
      var byte = _this__u8e3s4 >> shift & 255;
      var byteDigits = get_BYTE_TO_LOWER_CASE_HEX_DIGITS()[byte];
      var _unary__edvuaz = dstIndex;
      dstIndex = _unary__edvuaz + 1 | 0;
      dst[_unary__edvuaz] = toByte(byteDigits >> 8);
      var _unary__edvuaz_0 = dstIndex;
      dstIndex = _unary__edvuaz_0 + 1 | 0;
      dst[_unary__edvuaz_0] = toByte(byteDigits);
    }
     while (!(reversedIndex === last));
  return dstIndex;
}
function getIntAt(_this__u8e3s4, index) {
  return (_this__u8e3s4[index + 0 | 0] & 255) << 24 | (_this__u8e3s4[index + 1 | 0] & 255) << 16 | (_this__u8e3s4[index + 2 | 0] & 255) << 8 | _this__u8e3s4[index + 3 | 0] & 255;
}
function AbstractCollection$toString$lambda(this$0) {
  return (it) => it === this$0 ? '(this Collection)' : toString_0(it);
}
var Companion_instance_3;
function Companion_getInstance_3() {
  return Companion_instance_3;
}
function toString_2($this, entry) {
  return toString_3($this, entry.c1()) + '=' + toString_3($this, entry.d1());
}
function toString_3($this, o) {
  return o === $this ? '(this Map)' : toString_0(o);
}
function implFindEntry($this, key) {
  var tmp0 = $this.h1();
  var tmp$ret$0;
  $l$block: {
    // Inline function 'kotlin.collections.firstOrNull' call
    var _iterator__ex2g4s = tmp0.f();
    while (_iterator__ex2g4s.g()) {
      var element = _iterator__ex2g4s.h();
      if (equals(element.c1(), key)) {
        tmp$ret$0 = element;
        break $l$block;
      }
    }
    tmp$ret$0 = null;
  }
  return tmp$ret$0;
}
var Companion_instance_4;
function Companion_getInstance_4() {
  return Companion_instance_4;
}
function AbstractMap$toString$lambda(this$0) {
  return (it) => toString_2(this$0, it);
}
var Companion_instance_5;
function Companion_getInstance_5() {
  return Companion_instance_5;
}
function throwIndexOverflow() {
  throw ArithmeticException.r8('Index overflow has happened.');
}
function collectionToArrayCommonImpl(collection) {
  if (collection.s()) {
    // Inline function 'kotlin.emptyArray' call
    return [];
  }
  // Inline function 'kotlin.arrayOfNulls' call
  var size = collection.i();
  var destination = Array(size);
  var iterator = collection.f();
  var index = 0;
  while (iterator.g()) {
    var _unary__edvuaz = index;
    index = _unary__edvuaz + 1 | 0;
    destination[_unary__edvuaz] = iterator.h();
  }
  return destination;
}
function listOf_0(elements) {
  return elements.length > 0 ? asList(elements) : emptyList();
}
function emptyList() {
  return EmptyList_instance;
}
var EmptyList_instance;
function EmptyList_getInstance() {
  return EmptyList_instance;
}
var EmptyIterator_instance;
function EmptyIterator_getInstance() {
  return EmptyIterator_instance;
}
function collectionSizeOrDefault(_this__u8e3s4, default_0) {
  var tmp;
  if (isInterface(_this__u8e3s4, Collection)) {
    tmp = _this__u8e3s4.i();
  } else {
    tmp = default_0;
  }
  return tmp;
}
function toMap(_this__u8e3s4) {
  if (isInterface(_this__u8e3s4, Collection)) {
    var tmp;
    switch (_this__u8e3s4.i()) {
      case 0:
        tmp = emptyMap();
        break;
      case 1:
        var tmp_0;
        if (isInterface(_this__u8e3s4, KtList)) {
          tmp_0 = _this__u8e3s4.w(0);
        } else {
          tmp_0 = _this__u8e3s4.f().h();
        }

        tmp = mapOf(tmp_0);
        break;
      default:
        tmp = toMap_0(_this__u8e3s4, LinkedHashMap.w7(mapCapacity(_this__u8e3s4.i())));
        break;
    }
    return tmp;
  }
  return optimizeReadOnlyMap(toMap_0(_this__u8e3s4, LinkedHashMap.v7()));
}
function emptyMap() {
  var tmp = EmptyMap_instance;
  return isInterface(tmp, KtMap) ? tmp : THROW_CCE();
}
function toMap_0(_this__u8e3s4, destination) {
  // Inline function 'kotlin.apply' call
  putAll_0(destination, _this__u8e3s4);
  return destination;
}
function optimizeReadOnlyMap(_this__u8e3s4) {
  var tmp;
  switch (_this__u8e3s4.i()) {
    case 0:
      tmp = emptyMap();
      break;
    case 1:
      // Inline function 'kotlin.collections.toSingletonMapOrSelf' call

      tmp = _this__u8e3s4;
      break;
    default:
      tmp = _this__u8e3s4;
      break;
  }
  return tmp;
}
var EmptyMap_instance;
function EmptyMap_getInstance() {
  return EmptyMap_instance;
}
function putAll(_this__u8e3s4, pairs) {
  var inductionVariable = 0;
  var last = pairs.length;
  while (inductionVariable < last) {
    var _destruct__k2r9zo = pairs[inductionVariable];
    inductionVariable = inductionVariable + 1 | 0;
    var key = _destruct__k2r9zo.bb();
    var value = _destruct__k2r9zo.cb();
    _this__u8e3s4.e4(key, value);
  }
}
function putAll_0(_this__u8e3s4, pairs) {
  var _iterator__ex2g4s = pairs.f();
  while (_iterator__ex2g4s.g()) {
    var _destruct__k2r9zo = _iterator__ex2g4s.h();
    var key = _destruct__k2r9zo.bb();
    var value = _destruct__k2r9zo.cb();
    _this__u8e3s4.e4(key, value);
  }
}
function hashMapOf(pairs) {
  // Inline function 'kotlin.apply' call
  var this_0 = HashMap.h5(mapCapacity(pairs.length));
  putAll(this_0, pairs);
  return this_0;
}
var EmptySet_instance;
function EmptySet_getInstance() {
  return EmptySet_instance;
}
function getProgressionLastElement(start, end, step) {
  var tmp;
  if (step > 0) {
    tmp = start >= end ? end : end - differenceModulo(end, start, step) | 0;
  } else if (step < 0) {
    tmp = start <= end ? end : end + differenceModulo(start, end, -step | 0) | 0;
  } else {
    throw IllegalArgumentException.q2('Step is zero.');
  }
  return tmp;
}
function differenceModulo(a, b, c) {
  return mod(mod(a, c) - mod(b, c) | 0, c);
}
function mod(a, b) {
  var mod = a % b | 0;
  return mod >= 0 ? mod : mod + b | 0;
}
var Companion_instance_6;
function Companion_getInstance_6() {
  if (Companion_instance_6 === VOID) {
    Companion_instance_7;
    new Companion_6();
  }
  return Companion_instance_6;
}
var Companion_instance_7;
function Companion_getInstance_7() {
  return Companion_instance_7;
}
function appendElement(_this__u8e3s4, element, transform) {
  if (!(transform == null))
    _this__u8e3s4.e(transform(element));
  else {
    if (element == null ? true : isCharSequence(element))
      _this__u8e3s4.e(element);
    else {
      if (element instanceof Char)
        _this__u8e3s4.k7(element.pb_1);
      else {
        _this__u8e3s4.e(toString_1(element));
      }
    }
  }
}
function get_BYTE_TO_LOWER_CASE_HEX_DIGITS() {
  _init_properties_HexExtensions_kt__wu8rc3();
  return BYTE_TO_LOWER_CASE_HEX_DIGITS;
}
var BYTE_TO_LOWER_CASE_HEX_DIGITS;
var BYTE_TO_UPPER_CASE_HEX_DIGITS;
var HEX_DIGITS_TO_DECIMAL;
var HEX_DIGITS_TO_LONG_DECIMAL;
var properties_initialized_HexExtensions_kt_h16sbl;
function _init_properties_HexExtensions_kt__wu8rc3() {
  if (!properties_initialized_HexExtensions_kt_h16sbl) {
    properties_initialized_HexExtensions_kt_h16sbl = true;
    var tmp = 0;
    var tmp_0 = new Int32Array(256);
    while (tmp < 256) {
      var tmp_1 = tmp;
      // Inline function 'kotlin.code' call
      var this_0 = charCodeAt('0123456789abcdef', tmp_1 >> 4);
      var tmp_2 = Char__toInt_impl_vasixd(this_0) << 8;
      // Inline function 'kotlin.code' call
      var this_1 = charCodeAt('0123456789abcdef', tmp_1 & 15);
      tmp_0[tmp_1] = tmp_2 | Char__toInt_impl_vasixd(this_1);
      tmp = tmp + 1 | 0;
    }
    BYTE_TO_LOWER_CASE_HEX_DIGITS = tmp_0;
    var tmp_3 = 0;
    var tmp_4 = new Int32Array(256);
    while (tmp_3 < 256) {
      var tmp_5 = tmp_3;
      // Inline function 'kotlin.code' call
      var this_2 = charCodeAt('0123456789ABCDEF', tmp_5 >> 4);
      var tmp_6 = Char__toInt_impl_vasixd(this_2) << 8;
      // Inline function 'kotlin.code' call
      var this_3 = charCodeAt('0123456789ABCDEF', tmp_5 & 15);
      tmp_4[tmp_5] = tmp_6 | Char__toInt_impl_vasixd(this_3);
      tmp_3 = tmp_3 + 1 | 0;
    }
    BYTE_TO_UPPER_CASE_HEX_DIGITS = tmp_4;
    var tmp_7 = 0;
    var tmp_8 = new Int32Array(256);
    while (tmp_7 < 256) {
      tmp_8[tmp_7] = -1;
      tmp_7 = tmp_7 + 1 | 0;
    }
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.text.forEachIndexed' call
    var index = 0;
    var indexedObject = '0123456789abcdef';
    var inductionVariable = 0;
    while (inductionVariable < charSequenceLength(indexedObject)) {
      var item = charSequenceGet(indexedObject, inductionVariable);
      inductionVariable = inductionVariable + 1 | 0;
      var _unary__edvuaz = index;
      index = _unary__edvuaz + 1 | 0;
      // Inline function 'kotlin.code' call
      tmp_8[Char__toInt_impl_vasixd(item)] = _unary__edvuaz;
    }
    // Inline function 'kotlin.text.forEachIndexed' call
    var index_0 = 0;
    var indexedObject_0 = '0123456789ABCDEF';
    var inductionVariable_0 = 0;
    while (inductionVariable_0 < charSequenceLength(indexedObject_0)) {
      var item_0 = charSequenceGet(indexedObject_0, inductionVariable_0);
      inductionVariable_0 = inductionVariable_0 + 1 | 0;
      var _unary__edvuaz_0 = index_0;
      index_0 = _unary__edvuaz_0 + 1 | 0;
      // Inline function 'kotlin.code' call
      tmp_8[Char__toInt_impl_vasixd(item_0)] = _unary__edvuaz_0;
    }
    HEX_DIGITS_TO_DECIMAL = tmp_8;
    var tmp_9 = 0;
    var tmp_10 = new BigInt64Array(256);
    while (tmp_9 < 256) {
      tmp_10[tmp_9] = -1n;
      tmp_9 = tmp_9 + 1 | 0;
    }
    // Inline function 'kotlin.apply' call
    // Inline function 'kotlin.text.forEachIndexed' call
    var index_1 = 0;
    var indexedObject_1 = '0123456789abcdef';
    var inductionVariable_1 = 0;
    while (inductionVariable_1 < charSequenceLength(indexedObject_1)) {
      var item_1 = charSequenceGet(indexedObject_1, inductionVariable_1);
      inductionVariable_1 = inductionVariable_1 + 1 | 0;
      var _unary__edvuaz_1 = index_1;
      index_1 = _unary__edvuaz_1 + 1 | 0;
      // Inline function 'kotlin.code' call
      tmp_10[Char__toInt_impl_vasixd(item_1)] = fromInt_0(_unary__edvuaz_1);
    }
    // Inline function 'kotlin.text.forEachIndexed' call
    var index_2 = 0;
    var indexedObject_2 = '0123456789ABCDEF';
    var inductionVariable_2 = 0;
    while (inductionVariable_2 < charSequenceLength(indexedObject_2)) {
      var item_2 = charSequenceGet(indexedObject_2, inductionVariable_2);
      inductionVariable_2 = inductionVariable_2 + 1 | 0;
      var _unary__edvuaz_2 = index_2;
      index_2 = _unary__edvuaz_2 + 1 | 0;
      // Inline function 'kotlin.code' call
      tmp_10[Char__toInt_impl_vasixd(item_2)] = fromInt_0(_unary__edvuaz_2);
    }
    HEX_DIGITS_TO_LONG_DECIMAL = tmp_10;
  }
}
function isBlank(_this__u8e3s4) {
  var tmp$ret$0;
  $l$block: {
    // Inline function 'kotlin.text.all' call
    var inductionVariable = 0;
    while (inductionVariable < charSequenceLength(_this__u8e3s4)) {
      var element = charSequenceGet(_this__u8e3s4, inductionVariable);
      inductionVariable = inductionVariable + 1 | 0;
      if (!isWhitespace(element)) {
        tmp$ret$0 = false;
        break $l$block;
      }
    }
    tmp$ret$0 = true;
  }
  return tmp$ret$0;
}
var System_instance;
function System_getInstance() {
  return System_instance;
}
function get_POWERS_OF_TEN() {
  _init_properties_Instant_kt__2myitt();
  return POWERS_OF_TEN;
}
var POWERS_OF_TEN;
var asciiDigitPositionsInIsoStringAfterYear;
var colonsInIsoOffsetString;
var asciiDigitsInIsoOffsetString;
var Companion_instance_8;
function Companion_getInstance_8() {
  if (Companion_instance_8 === VOID)
    new Companion_8();
  return Companion_instance_8;
}
function formatIso(instant) {
  _init_properties_Instant_kt__2myitt();
  // Inline function 'kotlin.text.buildString' call
  // Inline function 'kotlin.apply' call
  var this_0 = StringBuilder.d();
  var ldt = Companion_instance_9.wb(instant);
  var number = ldt.xb_1;
  // Inline function 'kotlin.math.absoluteValue' call
  if (abs_0(number) < 1000) {
    var innerBuilder = StringBuilder.d();
    if (number >= 0) {
      // Inline function 'kotlin.text.deleteAt' call
      innerBuilder.ba(number + 10000 | 0).ca(0);
    } else {
      // Inline function 'kotlin.text.deleteAt' call
      innerBuilder.ba(number - 10000 | 0).ca(1);
    }
    this_0.e(innerBuilder);
  } else {
    if (number >= 10000) {
      this_0.k7(_Char___init__impl__6a9atx(43));
    }
    this_0.ba(number);
  }
  this_0.k7(_Char___init__impl__6a9atx(45));
  formatIso$appendTwoDigits(this_0, this_0, ldt.yb_1);
  this_0.k7(_Char___init__impl__6a9atx(45));
  formatIso$appendTwoDigits(this_0, this_0, ldt.zb_1);
  this_0.k7(_Char___init__impl__6a9atx(84));
  formatIso$appendTwoDigits(this_0, this_0, ldt.ac_1);
  this_0.k7(_Char___init__impl__6a9atx(58));
  formatIso$appendTwoDigits(this_0, this_0, ldt.bc_1);
  this_0.k7(_Char___init__impl__6a9atx(58));
  formatIso$appendTwoDigits(this_0, this_0, ldt.cc_1);
  if (!(ldt.dc_1 === 0)) {
    this_0.k7(_Char___init__impl__6a9atx(46));
    var zerosToStrip = 0;
    while ((ldt.dc_1 % get_POWERS_OF_TEN()[zerosToStrip + 1 | 0] | 0) === 0) {
      zerosToStrip = zerosToStrip + 1 | 0;
    }
    zerosToStrip = zerosToStrip - (zerosToStrip % 3 | 0) | 0;
    var numberToOutput = ldt.dc_1 / get_POWERS_OF_TEN()[zerosToStrip] | 0;
    this_0.j7(substring_0((numberToOutput + get_POWERS_OF_TEN()[9 - zerosToStrip | 0] | 0).toString(), 1));
  }
  this_0.k7(_Char___init__impl__6a9atx(90));
  return this_0.toString();
}
var Companion_instance_9;
function Companion_getInstance_9() {
  return Companion_instance_9;
}
function formatIso$appendTwoDigits(_this__u8e3s4, $this_buildString, number) {
  if (number < 10) {
    _this__u8e3s4.k7(_Char___init__impl__6a9atx(48));
  }
  $this_buildString.ba(number);
}
var properties_initialized_Instant_kt_xip69;
function _init_properties_Instant_kt__2myitt() {
  if (!properties_initialized_Instant_kt_xip69) {
    properties_initialized_Instant_kt_xip69 = true;
    // Inline function 'kotlin.intArrayOf' call
    POWERS_OF_TEN = new Int32Array([1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000]);
    // Inline function 'kotlin.intArrayOf' call
    asciiDigitPositionsInIsoStringAfterYear = new Int32Array([1, 2, 4, 5, 7, 8, 10, 11, 13, 14]);
    // Inline function 'kotlin.intArrayOf' call
    colonsInIsoOffsetString = new Int32Array([3, 6]);
    // Inline function 'kotlin.intArrayOf' call
    asciiDigitsInIsoOffsetString = new Int32Array([1, 2, 4, 5, 7, 8]);
  }
}
var static_init_called;
function static_init() {
  if (static_init_called)
    return Unit_instance;
  static_init_called = true;
  LazyThreadSafetyMode_SYNCHRONIZED_instance = new LazyThreadSafetyMode('SYNCHRONIZED', 0);
  LazyThreadSafetyMode_PUBLICATION_instance = new LazyThreadSafetyMode('PUBLICATION', 1);
  LazyThreadSafetyMode_NONE_instance = new LazyThreadSafetyMode('NONE', 2);
}
var LazyThreadSafetyMode_SYNCHRONIZED_instance;
var LazyThreadSafetyMode_PUBLICATION_instance;
var LazyThreadSafetyMode_NONE_instance;
var UNINITIALIZED_VALUE_instance;
function UNINITIALIZED_VALUE_getInstance() {
  return UNINITIALIZED_VALUE_instance;
}
function LazyThreadSafetyMode_PUBLICATION_getInstance() {
  static_init();
  return LazyThreadSafetyMode_PUBLICATION_instance;
}
function to(_this__u8e3s4, that) {
  return new Pair(_this__u8e3s4, that);
}
var Companion_instance_10;
function Companion_getInstance_10() {
  if (Companion_instance_10 === VOID)
    new Companion_10();
  return Companion_instance_10;
}
function truncateForErrorMessage(_this__u8e3s4, maxSize) {
  return joinToString_0(_this__u8e3s4, VOID, '[', ']', maxSize);
}
function secureRandomUuid() {
  // Inline function 'kotlin.also' call
  var this_0 = new Int8Array(16);
  secureRandomBytes(this_0);
  return uuidFromRandomBytes(this_0);
}
function uuidFromRandomBytes(randomBytes) {
  randomBytes[6] = toByte(randomBytes[6] & 15);
  randomBytes[6] = toByte(randomBytes[6] | 64);
  randomBytes[8] = toByte(randomBytes[8] & 63);
  randomBytes[8] = toByte(randomBytes[8] | 128);
  return Companion_getInstance_10().lc(randomBytes);
}
function _ULong___init__impl__c78o9k(data) {
  return data;
}
function _ULong___get_data__impl__fggpzb($this) {
  return $this;
}
function _UShort___init__impl__jigrne(data) {
  return data;
}
function _UShort___get_data__impl__g0245($this) {
  return $this;
}
function PolymorphicSerializer$descriptor$delegate$lambda$lambda(this$0) {
  return ($this$buildSerialDescriptor) => {
    $this$buildSerialDescriptor.bd('type', serializer(StringCompanionObject_instance).sc());
    $this$buildSerialDescriptor.bd('value', buildSerialDescriptor('kotlinx.serialization.Polymorphic<' + this$0.cd_1.s8() + '>', CONTEXTUAL_getInstance(), []));
    $this$buildSerialDescriptor.vc_1 = this$0.dd_1;
    return Unit_instance;
  };
}
function PolymorphicSerializer$descriptor$delegate$lambda(this$0) {
  return () => {
    var tmp = OPEN_getInstance();
    return withContext(buildSerialDescriptor('kotlinx.serialization.Polymorphic', tmp, [], PolymorphicSerializer$descriptor$delegate$lambda$lambda(this$0)), this$0.cd_1);
  };
}
function PolymorphicSerializer$_get_descriptor_$ref_8tw9if() {
  return constructCallableReference((p0) => p0.sc(), 1, 0, 1);
}
function init_kotlinx_serialization_SerializationException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.td_1);
}
function get_nullable(_this__u8e3s4) {
  var tmp;
  if (_this__u8e3s4.sc().xd()) {
    tmp = isInterface(_this__u8e3s4, KSerializer) ? _this__u8e3s4 : THROW_CCE();
  } else {
    tmp = new NullableSerializer(_this__u8e3s4);
  }
  return tmp;
}
function serializer(_this__u8e3s4) {
  return StringSerializer_getInstance();
}
function withContext(_this__u8e3s4, context) {
  return new ContextDescriptor(_this__u8e3s4, context);
}
function get_elementDescriptors(_this__u8e3s4) {
  // Inline function 'kotlin.collections.Iterable' call
  return new elementDescriptors$$inlined$Iterable$1(_this__u8e3s4);
}
function buildSerialDescriptor(serialName, kind, typeParameters, builder) {
  var tmp;
  if (builder === VOID) {
    tmp = buildSerialDescriptor$lambda;
  } else {
    tmp = builder;
  }
  builder = tmp;
  // Inline function 'kotlin.text.isNotBlank' call
  // Inline function 'kotlin.require' call
  if (!!isBlank(serialName)) {
    var message = 'Blank serial names are prohibited';
    throw IllegalArgumentException.q2(toString_1(message));
  }
  // Inline function 'kotlin.require' call
  if (!!equals(kind, CLASS_getInstance())) {
    var message_0 = "For StructureKind.CLASS please use 'buildClassSerialDescriptor' instead";
    throw IllegalArgumentException.q2(toString_1(message_0));
  }
  var sdBuilder = new ClassSerialDescriptorBuilder(serialName);
  builder(sdBuilder);
  return new SerialDescriptorImpl(serialName, kind, sdBuilder.wc_1.i(), toList(typeParameters), sdBuilder);
}
function _get__hashCode__tgwhef($this) {
  var tmp0 = $this.ve_1;
  var tmp = KProperty1;
  // Inline function 'kotlin.getValue' call
  getPropertyCallableRef('_hashCode', 1, tmp, SerialDescriptorImpl$_get__hashCode_$ref_2v7wzp(), null);
  return tmp0.d1();
}
function SerialDescriptorImpl$_hashCode$delegate$lambda(this$0) {
  return () => hashCodeImpl(this$0, this$0.ue_1);
}
function SerialDescriptorImpl$_get__hashCode_$ref_2v7wzp() {
  return constructCallableReference((p0) => _get__hashCode__tgwhef(p0), 1, 0, 2);
}
function buildSerialDescriptor$lambda(_this__u8e3s4) {
  return Unit_instance;
}
var INT_instance;
function INT_getInstance() {
  if (INT_instance === VOID)
    new INT();
  return INT_instance;
}
var STRING_instance;
function STRING_getInstance() {
  if (STRING_instance === VOID)
    new STRING();
  return STRING_instance;
}
var OPEN_instance;
function OPEN_getInstance() {
  if (OPEN_instance === VOID)
    new OPEN();
  return OPEN_instance;
}
var CONTEXTUAL_instance;
function CONTEXTUAL_getInstance() {
  if (CONTEXTUAL_instance === VOID)
    new CONTEXTUAL();
  return CONTEXTUAL_instance;
}
var CLASS_instance;
function CLASS_getInstance() {
  if (CLASS_instance === VOID)
    new CLASS();
  return CLASS_instance;
}
function get_EMPTY_DESCRIPTOR_ARRAY() {
  _init_properties_Platform_common_kt__3qzecs();
  return EMPTY_DESCRIPTOR_ARRAY;
}
var EMPTY_DESCRIPTOR_ARRAY;
function compactArray(_this__u8e3s4) {
  _init_properties_Platform_common_kt__3qzecs();
  // Inline function 'kotlin.takeUnless' call
  var tmp;
  // Inline function 'kotlin.collections.isNullOrEmpty' call
  if (!(_this__u8e3s4 == null || _this__u8e3s4.s())) {
    tmp = _this__u8e3s4;
  } else {
    tmp = null;
  }
  var tmp0_safe_receiver = tmp;
  var tmp_0;
  if (tmp0_safe_receiver == null) {
    tmp_0 = null;
  } else {
    // Inline function 'kotlin.collections.toTypedArray' call
    tmp_0 = copyToArray(tmp0_safe_receiver);
  }
  var tmp1_elvis_lhs = tmp_0;
  return tmp1_elvis_lhs == null ? get_EMPTY_DESCRIPTOR_ARRAY() : tmp1_elvis_lhs;
}
function cachedSerialNames(_this__u8e3s4) {
  _init_properties_Platform_common_kt__3qzecs();
  if (isInterface(_this__u8e3s4, CachedNames))
    return _this__u8e3s4.we();
  var result = HashSet.q(_this__u8e3s4.de());
  var inductionVariable = 0;
  var last = _this__u8e3s4.de();
  if (inductionVariable < last)
    do {
      var i = inductionVariable;
      inductionVariable = inductionVariable + 1 | 0;
      // Inline function 'kotlin.collections.plusAssign' call
      var element = _this__u8e3s4.ee(i);
      result.o(element);
    }
     while (inductionVariable < last);
  return result;
}
var properties_initialized_Platform_common_kt_i7q4ty;
function _init_properties_Platform_common_kt__3qzecs() {
  if (!properties_initialized_Platform_common_kt_i7q4ty) {
    properties_initialized_Platform_common_kt_i7q4ty = true;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    EMPTY_DESCRIPTOR_ARRAY = [];
  }
}
function throwMissingFieldException(seen, goldenMask, descriptor) {
  // Inline function 'kotlin.collections.mutableListOf' call
  var missingFields = ArrayList.k4();
  var missingFieldsBits = goldenMask & ~seen;
  var inductionVariable = 0;
  if (inductionVariable < 32)
    do {
      var i = inductionVariable;
      inductionVariable = inductionVariable + 1 | 0;
      if (!((missingFieldsBits & 1) === 0)) {
        // Inline function 'kotlin.collections.plusAssign' call
        var element = descriptor.ee(i);
        missingFields.o(element);
      }
      missingFieldsBits = missingFieldsBits >>> 1 | 0;
    }
     while (inductionVariable < 32);
  throw MissingFieldException.vd(missingFields, descriptor.be());
}
function _get_childSerializers__7vnyfa($this) {
  var tmp0 = $this.lf_1;
  var tmp = KProperty1;
  // Inline function 'kotlin.getValue' call
  getPropertyCallableRef('childSerializers', 1, tmp, PluginGeneratedSerialDescriptor$_get_childSerializers_$ref_e7suca(), null);
  return tmp0.d1();
}
function _get__hashCode__tgwhef_0($this) {
  var tmp0 = $this.nf_1;
  var tmp = KProperty1;
  // Inline function 'kotlin.getValue' call
  getPropertyCallableRef('_hashCode', 1, tmp, PluginGeneratedSerialDescriptor$_get__hashCode_$ref_cmj4vz(), null);
  return tmp0.d1();
}
function buildIndices($this) {
  var indices = HashMap.t4();
  var inductionVariable = 0;
  var last = $this.gf_1.length - 1 | 0;
  if (inductionVariable <= last)
    do {
      var i = inductionVariable;
      inductionVariable = inductionVariable + 1 | 0;
      // Inline function 'kotlin.collections.set' call
      var key = $this.gf_1[i];
      indices.e4(key, i);
    }
     while (inductionVariable <= last);
  return indices;
}
function PluginGeneratedSerialDescriptor$childSerializers$delegate$lambda(this$0) {
  return () => {
    var tmp0_safe_receiver = this$0.df_1;
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.of();
    return tmp1_elvis_lhs == null ? get_EMPTY_SERIALIZER_ARRAY() : tmp1_elvis_lhs;
  };
}
function PluginGeneratedSerialDescriptor$_get_childSerializers_$ref_e7suca() {
  return constructCallableReference((p0) => _get_childSerializers__7vnyfa(p0), 1, 0, 3);
}
function PluginGeneratedSerialDescriptor$typeParameterDescriptors$delegate$lambda(this$0) {
  return () => {
    var tmp0_safe_receiver = this$0.df_1;
    var tmp1_safe_receiver = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.pf();
    var tmp;
    if (tmp1_safe_receiver == null) {
      tmp = null;
    } else {
      // Inline function 'kotlin.collections.map' call
      // Inline function 'kotlin.collections.mapTo' call
      var destination = ArrayList.m(tmp1_safe_receiver.length);
      var inductionVariable = 0;
      var last = tmp1_safe_receiver.length;
      while (inductionVariable < last) {
        var item = tmp1_safe_receiver[inductionVariable];
        inductionVariable = inductionVariable + 1 | 0;
        var tmp$ret$2 = item.sc();
        destination.o(tmp$ret$2);
      }
      tmp = destination;
    }
    return compactArray(tmp);
  };
}
function PluginGeneratedSerialDescriptor$_get_typeParameterDescriptors_$ref_jk3pka() {
  return constructCallableReference((p0) => p0.qf(), 1, 0, 4);
}
function PluginGeneratedSerialDescriptor$_hashCode$delegate$lambda(this$0) {
  return () => hashCodeImpl(this$0, this$0.qf());
}
function PluginGeneratedSerialDescriptor$_get__hashCode_$ref_cmj4vz() {
  return constructCallableReference((p0) => _get__hashCode__tgwhef_0(p0), 1, 0, 5);
}
function hashCodeImpl(_this__u8e3s4, typeParams) {
  var result = getStringHashCode(_this__u8e3s4.be());
  result = imul_0(31, result) + contentHashCode(typeParams) | 0;
  var elementDescriptors = get_elementDescriptors(_this__u8e3s4);
  // Inline function 'kotlinx.serialization.internal.elementsHashCodeBy' call
  // Inline function 'kotlin.collections.fold' call
  var accumulator = 1;
  var _iterator__ex2g4s = elementDescriptors.f();
  while (_iterator__ex2g4s.g()) {
    var element = _iterator__ex2g4s.h();
    var hash = accumulator;
    var tmp = imul_0(31, hash);
    // Inline function 'kotlin.hashCode' call
    var tmp0_safe_receiver = element.be();
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : hashCode(tmp0_safe_receiver);
    accumulator = tmp + (tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs) | 0;
  }
  var namesHash = accumulator;
  // Inline function 'kotlinx.serialization.internal.elementsHashCodeBy' call
  // Inline function 'kotlin.collections.fold' call
  var accumulator_0 = 1;
  var _iterator__ex2g4s_0 = elementDescriptors.f();
  while (_iterator__ex2g4s_0.g()) {
    var element_0 = _iterator__ex2g4s_0.h();
    var hash_0 = accumulator_0;
    var tmp_0 = imul_0(31, hash_0);
    // Inline function 'kotlin.hashCode' call
    var tmp0_safe_receiver_0 = element_0.ce();
    var tmp1_elvis_lhs_0 = tmp0_safe_receiver_0 == null ? null : hashCode(tmp0_safe_receiver_0);
    accumulator_0 = tmp_0 + (tmp1_elvis_lhs_0 == null ? 0 : tmp1_elvis_lhs_0) | 0;
  }
  var kindHash = accumulator_0;
  result = imul_0(31, result) + namesHash | 0;
  result = imul_0(31, result) + kindHash | 0;
  return result;
}
function toStringImpl(_this__u8e3s4) {
  var tmp = until(0, _this__u8e3s4.de());
  var tmp_0 = _this__u8e3s4.be() + '(';
  return joinToString_1(tmp, ', ', tmp_0, ')', VOID, VOID, toStringImpl$lambda(_this__u8e3s4));
}
function toStringImpl$lambda($this_toStringImpl) {
  return (i) => $this_toStringImpl.ee(i) + ': ' + $this_toStringImpl.fe(i).be();
}
function get_EMPTY_SERIALIZER_ARRAY() {
  _init_properties_PluginHelperInterfaces_kt__xgvzfp();
  return EMPTY_SERIALIZER_ARRAY;
}
var EMPTY_SERIALIZER_ARRAY;
var properties_initialized_PluginHelperInterfaces_kt_ap8in1;
function _init_properties_PluginHelperInterfaces_kt__xgvzfp() {
  if (!properties_initialized_PluginHelperInterfaces_kt_ap8in1) {
    properties_initialized_PluginHelperInterfaces_kt_ap8in1 = true;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    EMPTY_SERIALIZER_ARRAY = [];
  }
}
var StringSerializer_instance;
function StringSerializer_getInstance() {
  if (StringSerializer_instance === VOID)
    new StringSerializer();
  return StringSerializer_instance;
}
var IntSerializer_instance;
function IntSerializer_getInstance() {
  if (IntSerializer_instance === VOID)
    new IntSerializer();
  return IntSerializer_instance;
}
function error($this) {
  throw IllegalStateException.e2('Primitive descriptor ' + $this.uf_1 + ' does not have elements');
}
function getChecked(_this__u8e3s4, index) {
  if (!(0 <= index ? index <= (_this__u8e3s4.length - 1 | 0) : false))
    throw IndexOutOfBoundsException.l8('Index ' + index + ' out of bounds ' + get_indices(_this__u8e3s4).toString());
  return _this__u8e3s4[index];
}
var Companion_instance_11;
function Companion_getInstance_11() {
  if (Companion_instance_11 === VOID)
    new Companion_11();
  return Companion_instance_11;
}
var Companion_instance_12;
function Companion_getInstance_12() {
  return Companion_instance_12;
}
var $serializer_instance;
function $serializer_getInstance() {
  if ($serializer_instance === VOID)
    new $serializer();
  return $serializer_instance;
}
var Companion_instance_13;
function Companion_getInstance_13() {
  return Companion_instance_13;
}
var Companion_instance_14;
function Companion_getInstance_14() {
  if (Companion_instance_14 === VOID)
    new Companion_14();
  return Companion_instance_14;
}
function PageQuery$Companion$$childSerializers$_anonymous__54lki7() {
  var tmp = getKClass(OffsetPaginationDTO);
  // Inline function 'kotlin.arrayOf' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  var tmp$ret$0 = [];
  return PolymorphicSerializer.hd(tmp, tmp$ret$0);
}
var Companion_instance_15;
function Companion_getInstance_15() {
  if (Companion_instance_15 === VOID)
    new Companion_15();
  return Companion_instance_15;
}
var $serializer_instance_0;
function $serializer_getInstance_0() {
  if ($serializer_instance_0 === VOID)
    new $serializer_0();
  return $serializer_instance_0;
}
var Companion_instance_16;
function Companion_getInstance_16() {
  if (Companion_instance_16 === VOID)
    new Companion_16();
  return Companion_instance_16;
}
var Companion_instance_17;
function Companion_getInstance_17() {
  return Companion_instance_17;
}
var Companion_instance_18;
function Companion_getInstance_18() {
  return Companion_instance_18;
}
var $serializer_instance_1;
function $serializer_getInstance_1() {
  if ($serializer_instance_1 === VOID)
    new $serializer_1();
  return $serializer_instance_1;
}
var Companion_instance_19;
function Companion_getInstance_19() {
  return Companion_instance_19;
}
var $serializer_instance_2;
function $serializer_getInstance_2() {
  if ($serializer_instance_2 === VOID)
    new $serializer_2();
  return $serializer_instance_2;
}
var Companion_instance_20;
function Companion_getInstance_20() {
  return Companion_instance_20;
}
var $serializer_instance_3;
function $serializer_getInstance_3() {
  if ($serializer_instance_3 === VOID)
    new $serializer_3();
  return $serializer_instance_3;
}
var static_init_called_0;
function static_init_0() {
  if (static_init_called_0)
    return Unit_instance;
  static_init_called_0 = true;
  ImPermission_IM_FORCE_MFA_OTP_instance = new ImPermission('IM_FORCE_MFA_OTP', 0, 'im_mfa_force_otp');
  ImPermission_IM_USER_READ_instance = new ImPermission('IM_USER_READ', 1, 'im_user_read');
  ImPermission_IM_USER_ROLE_WRITE_instance = new ImPermission('IM_USER_ROLE_WRITE', 2, 'im_user_role_write');
  ImPermission_IM_USER_WRITE_instance = new ImPermission('IM_USER_WRITE', 3, 'im_user_write');
  ImPermission_IM_MY_USER_instance = new ImPermission('IM_MY_USER', 4, 'im_user_self');
  ImPermission_IM_ORGANIZATION_READ_instance = new ImPermission('IM_ORGANIZATION_READ', 5, 'im_organization_read');
  ImPermission_IM_ORGANIZATION_WRITE_instance = new ImPermission('IM_ORGANIZATION_WRITE', 6, 'im_organization_write');
  ImPermission_IM_MY_ORGANIZATION_WRITE_instance = new ImPermission('IM_MY_ORGANIZATION_WRITE', 7, 'im_organization_write_own');
  ImPermission_IM_MY_ORGANIZATION_READ_instance = new ImPermission('IM_MY_ORGANIZATION_READ', 8, 'im_organization_read_own');
  ImPermission_IM_ORGANIZATION_API_KEY_READ_instance = new ImPermission('IM_ORGANIZATION_API_KEY_READ', 9, 'im_organization_apikey_read');
  ImPermission_IM_ORGANIZATION_STATUS_WRITE_instance = new ImPermission('IM_ORGANIZATION_STATUS_WRITE', 10, 'im_organization_status_write');
  ImPermission_IM_APIKEY_READ_instance = new ImPermission('IM_APIKEY_READ', 11, 'im_apikey_read');
  ImPermission_IM_APIKEY_WRITE_instance = new ImPermission('IM_APIKEY_WRITE', 12, 'im_apikey_write');
  ImPermission_IM_SPACE_READ_instance = new ImPermission('IM_SPACE_READ', 13, 'im_space_read');
  ImPermission_IM_SPACE_WRITE_instance = new ImPermission('IM_SPACE_WRITE', 14, 'im_space_write');
  ImPermission_IM_ROLE_READ_instance = new ImPermission('IM_ROLE_READ', 15, 'im_role_read');
  ImPermission_IM_ROLE_WRITE_instance = new ImPermission('IM_ROLE_WRITE', 16, 'im_role_write');
}
var ImPermission_IM_FORCE_MFA_OTP_instance;
var ImPermission_IM_USER_READ_instance;
var ImPermission_IM_USER_ROLE_WRITE_instance;
var ImPermission_IM_USER_WRITE_instance;
var ImPermission_IM_MY_USER_instance;
var ImPermission_IM_ORGANIZATION_READ_instance;
var ImPermission_IM_ORGANIZATION_WRITE_instance;
var ImPermission_IM_MY_ORGANIZATION_WRITE_instance;
var ImPermission_IM_MY_ORGANIZATION_READ_instance;
var ImPermission_IM_ORGANIZATION_API_KEY_READ_instance;
var ImPermission_IM_ORGANIZATION_STATUS_WRITE_instance;
var ImPermission_IM_APIKEY_READ_instance;
var ImPermission_IM_APIKEY_WRITE_instance;
var ImPermission_IM_SPACE_READ_instance;
var ImPermission_IM_SPACE_WRITE_instance;
var ImPermission_IM_ROLE_READ_instance;
var ImPermission_IM_ROLE_WRITE_instance;
function values() {
  static_init_0();
  return [ImPermission_IM_FORCE_MFA_OTP_getInstance(), ImPermission_IM_USER_READ_getInstance(), ImPermission_IM_USER_ROLE_WRITE_getInstance(), ImPermission_IM_USER_WRITE_getInstance(), ImPermission_IM_MY_USER_getInstance(), ImPermission_IM_ORGANIZATION_READ_getInstance(), ImPermission_IM_ORGANIZATION_WRITE_getInstance(), ImPermission_IM_MY_ORGANIZATION_WRITE_getInstance(), ImPermission_IM_MY_ORGANIZATION_READ_getInstance(), ImPermission_IM_ORGANIZATION_API_KEY_READ_getInstance(), ImPermission_IM_ORGANIZATION_STATUS_WRITE_getInstance(), ImPermission_IM_APIKEY_READ_getInstance(), ImPermission_IM_APIKEY_WRITE_getInstance(), ImPermission_IM_SPACE_READ_getInstance(), ImPermission_IM_SPACE_WRITE_getInstance(), ImPermission_IM_ROLE_READ_getInstance(), ImPermission_IM_ROLE_WRITE_getInstance()];
}
function valueOf(value) {
  static_init_0();
  switch (value) {
    case 'IM_FORCE_MFA_OTP':
      return ImPermission_IM_FORCE_MFA_OTP_getInstance();
    case 'IM_USER_READ':
      return ImPermission_IM_USER_READ_getInstance();
    case 'IM_USER_ROLE_WRITE':
      return ImPermission_IM_USER_ROLE_WRITE_getInstance();
    case 'IM_USER_WRITE':
      return ImPermission_IM_USER_WRITE_getInstance();
    case 'IM_MY_USER':
      return ImPermission_IM_MY_USER_getInstance();
    case 'IM_ORGANIZATION_READ':
      return ImPermission_IM_ORGANIZATION_READ_getInstance();
    case 'IM_ORGANIZATION_WRITE':
      return ImPermission_IM_ORGANIZATION_WRITE_getInstance();
    case 'IM_MY_ORGANIZATION_WRITE':
      return ImPermission_IM_MY_ORGANIZATION_WRITE_getInstance();
    case 'IM_MY_ORGANIZATION_READ':
      return ImPermission_IM_MY_ORGANIZATION_READ_getInstance();
    case 'IM_ORGANIZATION_API_KEY_READ':
      return ImPermission_IM_ORGANIZATION_API_KEY_READ_getInstance();
    case 'IM_ORGANIZATION_STATUS_WRITE':
      return ImPermission_IM_ORGANIZATION_STATUS_WRITE_getInstance();
    case 'IM_APIKEY_READ':
      return ImPermission_IM_APIKEY_READ_getInstance();
    case 'IM_APIKEY_WRITE':
      return ImPermission_IM_APIKEY_WRITE_getInstance();
    case 'IM_SPACE_READ':
      return ImPermission_IM_SPACE_READ_getInstance();
    case 'IM_SPACE_WRITE':
      return ImPermission_IM_SPACE_WRITE_getInstance();
    case 'IM_ROLE_READ':
      return ImPermission_IM_ROLE_READ_getInstance();
    case 'IM_ROLE_WRITE':
      return ImPermission_IM_ROLE_WRITE_getInstance();
    default:
      THROW_IAE('No enum constant io.komune.im.commons.auth.ImPermission.' + value);
      break;
  }
}
function ImPermission_IM_FORCE_MFA_OTP_getInstance() {
  static_init_0();
  return ImPermission_IM_FORCE_MFA_OTP_instance;
}
function ImPermission_IM_USER_READ_getInstance() {
  static_init_0();
  return ImPermission_IM_USER_READ_instance;
}
function ImPermission_IM_USER_ROLE_WRITE_getInstance() {
  static_init_0();
  return ImPermission_IM_USER_ROLE_WRITE_instance;
}
function ImPermission_IM_USER_WRITE_getInstance() {
  static_init_0();
  return ImPermission_IM_USER_WRITE_instance;
}
function ImPermission_IM_MY_USER_getInstance() {
  static_init_0();
  return ImPermission_IM_MY_USER_instance;
}
function ImPermission_IM_ORGANIZATION_READ_getInstance() {
  static_init_0();
  return ImPermission_IM_ORGANIZATION_READ_instance;
}
function ImPermission_IM_ORGANIZATION_WRITE_getInstance() {
  static_init_0();
  return ImPermission_IM_ORGANIZATION_WRITE_instance;
}
function ImPermission_IM_MY_ORGANIZATION_WRITE_getInstance() {
  static_init_0();
  return ImPermission_IM_MY_ORGANIZATION_WRITE_instance;
}
function ImPermission_IM_MY_ORGANIZATION_READ_getInstance() {
  static_init_0();
  return ImPermission_IM_MY_ORGANIZATION_READ_instance;
}
function ImPermission_IM_ORGANIZATION_API_KEY_READ_getInstance() {
  static_init_0();
  return ImPermission_IM_ORGANIZATION_API_KEY_READ_instance;
}
function ImPermission_IM_ORGANIZATION_STATUS_WRITE_getInstance() {
  static_init_0();
  return ImPermission_IM_ORGANIZATION_STATUS_WRITE_instance;
}
function ImPermission_IM_APIKEY_READ_getInstance() {
  static_init_0();
  return ImPermission_IM_APIKEY_READ_instance;
}
function ImPermission_IM_APIKEY_WRITE_getInstance() {
  static_init_0();
  return ImPermission_IM_APIKEY_WRITE_instance;
}
function ImPermission_IM_SPACE_READ_getInstance() {
  static_init_0();
  return ImPermission_IM_SPACE_READ_instance;
}
function ImPermission_IM_SPACE_WRITE_getInstance() {
  static_init_0();
  return ImPermission_IM_SPACE_WRITE_instance;
}
function ImPermission_IM_ROLE_READ_getInstance() {
  static_init_0();
  return ImPermission_IM_ROLE_READ_instance;
}
function ImPermission_IM_ROLE_WRITE_getInstance() {
  static_init_0();
  return ImPermission_IM_ROLE_WRITE_instance;
}
var ExceptionCodes_instance;
function ExceptionCodes_getInstance() {
  return ExceptionCodes_instance;
}
var Companion_instance_21;
function Companion_getInstance_21() {
  return Companion_instance_21;
}
var $serializer_instance_4;
function $serializer_getInstance_4() {
  if ($serializer_instance_4 === VOID)
    new $serializer_4();
  return $serializer_instance_4;
}
//region block: post-declaration
initMetadataForInterface(CharSequence, 'CharSequence');
initMetadataForClass(Number_0, 'Number');
initMetadataForClass(Char, 'Char');
initMetadataForInterface(Collection, 'Collection');
initMetadataForInterface(KtSet, 'Set', VOID, VOID, [Collection]);
initMetadataForCompanion(Companion);
initMetadataForInterface(KtList, 'List', VOID, VOID, [Collection]);
initMetadataForInterface(Entry, 'Entry');
initMetadataForInterface(KtMap, 'Map');
initMetadataForCompanion(Companion_0);
initMetadataForClass(Enum, 'Enum');
initMetadataForClass(Long, 'Long');
initMetadataForInterface(FunctionAdapter, 'FunctionAdapter');
initMetadataForClass(arrayIterator$1);
initMetadataForClass(JsArrayView, 'JsArrayView', JsArrayView);
initMetadataForObject(StringCompanionObject, 'StringCompanionObject');
initMetadataForInterface(Comparator, 'Comparator');
initMetadataForObject(Unit, 'Unit');
initMetadataForClass(AbstractCollection, 'AbstractCollection', VOID, VOID, [Collection]);
initMetadataForClass(AbstractMutableCollection, 'AbstractMutableCollection', VOID, VOID, [Collection]);
initMetadataForClass(IteratorImpl, 'IteratorImpl');
initMetadataForClass(ListIteratorImpl, 'ListIteratorImpl');
protoOf(AbstractMutableList).asJsReadonlyArrayView = asJsReadonlyArrayView;
initMetadataForClass(AbstractMutableList, 'AbstractMutableList', VOID, VOID, [KtList, Collection]);
initMetadataForClass(SubList, 'SubList');
initMetadataForClass(AbstractMap, 'AbstractMap', VOID, VOID, [KtMap]);
initMetadataForClass(AbstractMutableMap, 'AbstractMutableMap', VOID, VOID, [KtMap]);
initMetadataForClass(AbstractMutableSet, 'AbstractMutableSet', VOID, VOID, [Collection, KtSet]);
initMetadataForCompanion(Companion_1);
initMetadataForClass(ArrayList, 'ArrayList', ArrayList.k4, VOID, [KtList, Collection]);
initMetadataForClass(HashMap, 'HashMap', HashMap.t4, VOID, [KtMap]);
initMetadataForClass(HashMapKeys, 'HashMapKeys', VOID, VOID, [Collection, KtSet]);
initMetadataForClass(HashMapEntrySetBase, 'HashMapEntrySetBase', VOID, VOID, [Collection, KtSet]);
initMetadataForClass(HashMapEntrySet, 'HashMapEntrySet');
initMetadataForClass(HashMapKeysDefault$iterator$1);
initMetadataForClass(HashMapKeysDefault, 'HashMapKeysDefault');
initMetadataForClass(HashSet, 'HashSet', HashSet.z5, VOID, [Collection, KtSet]);
initMetadataForCompanion(Companion_2);
initMetadataForClass(Itr, 'Itr');
initMetadataForClass(KeysItr, 'KeysItr');
initMetadataForClass(EntriesItr, 'EntriesItr');
initMetadataForClass(EntryRef, 'EntryRef', VOID, VOID, [Entry]);
initMetadataForInterface(InternalMap, 'InternalMap');
protoOf(InternalHashMap).u5 = containsAllEntries;
initMetadataForClass(InternalHashMap, 'InternalHashMap', InternalHashMap.e5, VOID, [InternalMap]);
initMetadataForClass(LinkedHashMap, 'LinkedHashMap', LinkedHashMap.v7, VOID, [KtMap]);
initMetadataForClass(Exception, 'Exception', Exception.e8);
initMetadataForClass(RuntimeException, 'RuntimeException', RuntimeException.x7);
initMetadataForClass(UnsupportedOperationException, 'UnsupportedOperationException', UnsupportedOperationException.a2);
initMetadataForClass(IllegalArgumentException, 'IllegalArgumentException', IllegalArgumentException.a8);
initMetadataForClass(NoSuchElementException, 'NoSuchElementException', NoSuchElementException.x2);
initMetadataForClass(IndexOutOfBoundsException, 'IndexOutOfBoundsException', IndexOutOfBoundsException.k8);
initMetadataForClass(IllegalStateException, 'IllegalStateException', IllegalStateException.m8);
initMetadataForClass(ConcurrentModificationException, 'ConcurrentModificationException', ConcurrentModificationException.x6);
initMetadataForClass(ArithmeticException, 'ArithmeticException', ArithmeticException.q8);
initMetadataForClass(NullPointerException, 'NullPointerException', NullPointerException.i2);
initMetadataForClass(ClassCastException, 'ClassCastException', ClassCastException.m2);
initMetadataForInterface(KClass, 'KClass');
initMetadataForClass(KClassImpl, 'KClassImpl', VOID, VOID, [KClass]);
initMetadataForClass(PrimitiveKClassImpl, 'PrimitiveKClassImpl');
initMetadataForObject(NothingKClassImpl, 'NothingKClassImpl');
initMetadataForClass(SimpleKClassImpl, 'SimpleKClassImpl');
initMetadataForInterface(KProperty1, 'KProperty1');
initMetadataForObject(PrimitiveClasses, 'PrimitiveClasses');
initMetadataForClass(CharacterCodingException, 'CharacterCodingException', CharacterCodingException.z9);
initMetadataForClass(StringBuilder, 'StringBuilder', StringBuilder.d, VOID, [CharSequence]);
initMetadataForClass(sam$kotlin_Comparator$0, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
initMetadataForCompanion(Companion_3);
initMetadataForClass(AbstractMap$keys$1$iterator$1);
initMetadataForCompanion(Companion_4);
initMetadataForClass(AbstractSet, 'AbstractSet', VOID, VOID, [KtSet]);
initMetadataForClass(AbstractMap$keys$1);
initMetadataForCompanion(Companion_5);
protoOf(EmptyList).asJsReadonlyArrayView = asJsReadonlyArrayView;
initMetadataForObject(EmptyList, 'EmptyList', VOID, VOID, [KtList]);
initMetadataForObject(EmptyIterator, 'EmptyIterator');
initMetadataForClass(IndexedValue, 'IndexedValue');
initMetadataForClass(IndexingIterable, 'IndexingIterable');
initMetadataForClass(IndexingIterator, 'IndexingIterator');
initMetadataForObject(EmptyMap, 'EmptyMap', VOID, VOID, [KtMap]);
initMetadataForClass(IntIterator, 'IntIterator');
initMetadataForObject(EmptySet, 'EmptySet', VOID, VOID, [KtSet]);
initMetadataForCompanion(Companion_6);
initMetadataForClass(IntProgression, 'IntProgression');
initMetadataForClass(IntRange, 'IntRange');
initMetadataForClass(IntProgressionIterator, 'IntProgressionIterator');
initMetadataForCompanion(Companion_7);
initMetadataForObject(System, 'System');
initMetadataForCompanion(Companion_8);
initMetadataForClass(Instant, 'Instant');
initMetadataForCompanion(Companion_9);
initMetadataForClass(UnboundLocalDateTime, 'UnboundLocalDateTime');
initMetadataForClass(LazyThreadSafetyMode, 'LazyThreadSafetyMode');
initMetadataForClass(UnsafeLazyImpl, 'UnsafeLazyImpl');
initMetadataForObject(UNINITIALIZED_VALUE, 'UNINITIALIZED_VALUE');
initMetadataForClass(Pair, 'Pair');
initMetadataForCompanion(Companion_10);
initMetadataForClass(Uuid, 'Uuid');
initMetadataForInterface(KSerializer, 'KSerializer');
initMetadataForClass(AbstractPolymorphicSerializer, 'AbstractPolymorphicSerializer', VOID, VOID, [KSerializer]);
initMetadataForClass(PolymorphicSerializer, 'PolymorphicSerializer');
initMetadataForClass(SerializationException, 'SerializationException', SerializationException.wd);
initMetadataForClass(MissingFieldException, 'MissingFieldException');
initMetadataForInterface(SerialDescriptor, 'SerialDescriptor');
initMetadataForClass(ContextDescriptor, 'ContextDescriptor', VOID, VOID, [SerialDescriptor]);
initMetadataForClass(elementDescriptors$1);
initMetadataForClass(elementDescriptors$$inlined$Iterable$1);
initMetadataForClass(ClassSerialDescriptorBuilder, 'ClassSerialDescriptorBuilder');
initMetadataForInterface(CachedNames, 'CachedNames');
protoOf(SerialDescriptorImpl).xd = get_isNullable;
initMetadataForClass(SerialDescriptorImpl, 'SerialDescriptorImpl', VOID, VOID, [SerialDescriptor, CachedNames]);
initMetadataForClass(SerialKind, 'SerialKind');
initMetadataForClass(PrimitiveKind, 'PrimitiveKind');
initMetadataForObject(INT, 'INT');
initMetadataForObject(STRING, 'STRING');
initMetadataForClass(PolymorphicKind, 'PolymorphicKind');
initMetadataForObject(OPEN, 'OPEN');
initMetadataForObject(CONTEXTUAL, 'CONTEXTUAL');
initMetadataForClass(StructureKind, 'StructureKind');
initMetadataForObject(CLASS, 'CLASS');
initMetadataForClass(NullableSerializer, 'NullableSerializer', VOID, VOID, [KSerializer]);
initMetadataForClass(SerialDescriptorForNullable, 'SerialDescriptorForNullable', VOID, VOID, [SerialDescriptor, CachedNames]);
protoOf(PluginGeneratedSerialDescriptor).xd = get_isNullable;
initMetadataForClass(PluginGeneratedSerialDescriptor, 'PluginGeneratedSerialDescriptor', VOID, VOID, [SerialDescriptor, CachedNames]);
initMetadataForInterface(GeneratedSerializer, 'GeneratedSerializer', VOID, VOID, [KSerializer]);
initMetadataForObject(StringSerializer, 'StringSerializer', VOID, VOID, [KSerializer]);
initMetadataForObject(IntSerializer, 'IntSerializer', VOID, VOID, [KSerializer]);
protoOf(PrimitiveSerialDescriptor).xd = get_isNullable;
initMetadataForClass(PrimitiveSerialDescriptor, 'PrimitiveSerialDescriptor', VOID, VOID, [SerialDescriptor]);
initMetadataForClass(SerializableWith, 'SerializableWith', VOID, VOID, VOID, VOID, 0);
initMetadataForInterface(Message, 'Message');
initMetadataForInterface(Command, 'Command', VOID, VOID, [Message]);
initMetadataForInterface(Event, 'Event', VOID, VOID, [Message]);
initMetadataForInterface(Problem, 'Problem', VOID, VOID, [Message]);
initMetadataForInterface(Query, 'Query', VOID, VOID, [Message]);
initMetadataForInterface(WithEnvelopeId, 'WithEnvelopeId');
initMetadataForInterface(WithEnvelopeData, 'WithEnvelopeData');
initMetadataForInterface(EnvelopeDTO, 'EnvelopeDTO', VOID, VOID, [WithEnvelopeId, WithEnvelopeData, Message]);
initMetadataForCompanion(Companion_11);
initMetadataForClass(Envelope, 'Envelope', VOID, VOID, [EnvelopeDTO], VOID, VOID, {0: Companion_getInstance_11});
initMetadataForInterface(F2ErrorDTO, 'F2ErrorDTO', VOID, VOID, [Problem]);
initMetadataForCompanion(Companion_12);
protoOf($serializer).pf = typeParametersSerializers;
initMetadataForObject($serializer, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(F2Error, 'F2Error', VOID, VOID, [F2ErrorDTO], VOID, VOID, {0: $serializer_getInstance});
initMetadataForCompanion(Companion_13);
initMetadataForClass(F2Exception, 'F2Exception');
initMetadataForInterface(Match, 'Match');
protoOf(AndMatch).or = or;
initMetadataForClass(AndMatch, 'AndMatch', VOID, VOID, [Match]);
protoOf(OrMatch).and = and;
initMetadataForClass(OrMatch, 'OrMatch', VOID, VOID, [Match]);
initMetadataForInterface(SortDTO, 'SortDTO');
initMetadataForInterface(PageDTO, 'PageDTO');
initMetadataForCompanion(Companion_14);
initMetadataForClass(Page, 'Page', VOID, VOID, [PageDTO], VOID, VOID, {0: Companion_getInstance_14});
initMetadataForInterface(PageQueryDTO, 'PageQueryDTO', VOID, VOID, [Query]);
initMetadataForInterface(PageQueryResultDTO, 'PageQueryResultDTO', VOID, VOID, [Event, PageDTO]);
initMetadataForCompanion(Companion_15);
protoOf($serializer_0).pf = typeParametersSerializers;
initMetadataForObject($serializer_0, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(PageQuery, 'PageQuery', VOID, VOID, [PageQueryDTO], VOID, VOID, {0: $serializer_getInstance_0});
initMetadataForCompanion(Companion_16);
initMetadataForClass(PageQueryResult, 'PageQueryResult', VOID, VOID, [PageQueryResultDTO], VOID, VOID, {0: Companion_getInstance_16});
initMetadataForCompanion(Companion_17);
initMetadataForInterface(Pagination, 'Pagination', VOID, VOID, VOID, VOID, VOID, {0: Companion_getInstance_17});
initMetadataForInterface(OffsetPaginationDTO, 'OffsetPaginationDTO', VOID, VOID, [Pagination]);
initMetadataForInterface(PagePaginationDTO, 'PagePaginationDTO', VOID, VOID, [Pagination]);
initMetadataForCompanion(Companion_18);
protoOf($serializer_1).pf = typeParametersSerializers;
initMetadataForObject($serializer_1, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OffsetPagination, 'OffsetPagination', VOID, VOID, [OffsetPaginationDTO], VOID, VOID, {0: $serializer_getInstance_1});
initMetadataForCompanion(Companion_19);
protoOf($serializer_2).pf = typeParametersSerializers;
initMetadataForObject($serializer_2, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(PagePagination, 'PagePagination', VOID, VOID, [PagePaginationDTO], VOID, VOID, {0: $serializer_getInstance_2});
initMetadataForClass(AuthRealm, 'AuthRealm');
initMetadataForClass(AuthRealmPassword, 'AuthRealmPassword');
initMetadataForClass(AuthRealmClientSecret, 'AuthRealmClientSecret');
initMetadataForCompanion(Companion_20);
protoOf($serializer_3).pf = typeParametersSerializers;
initMetadataForObject($serializer_3, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(TokenInfo, 'TokenInfo', VOID, VOID, VOID, VOID, VOID, {0: $serializer_getInstance_3});
initMetadataForInterface(AuthedUserDTO, 'AuthedUserDTO');
initMetadataForClass(ImPermission, 'ImPermission');
initMetadataForObject(ExceptionCodes, 'ExceptionCodes');
initMetadataForInterface(AddressDTO, 'AddressDTO');
initMetadataForCompanion(Companion_21);
protoOf($serializer_4).pf = typeParametersSerializers;
initMetadataForObject($serializer_4, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(Address, 'Address', VOID, VOID, [AddressDTO], VOID, VOID, {0: $serializer_getInstance_4});
//endregion
//region block: init
Companion_instance = new Companion();
Companion_instance_0 = new Companion_0();
StringCompanionObject_instance = new StringCompanionObject();
Unit_instance = new Unit();
Companion_instance_2 = new Companion_2();
Companion_instance_3 = new Companion_3();
Companion_instance_4 = new Companion_4();
Companion_instance_5 = new Companion_5();
EmptyList_instance = new EmptyList();
EmptyIterator_instance = new EmptyIterator();
EmptyMap_instance = new EmptyMap();
EmptySet_instance = new EmptySet();
Companion_instance_7 = new Companion_7();
System_instance = new System();
Companion_instance_9 = new Companion_9();
UNINITIALIZED_VALUE_instance = new UNINITIALIZED_VALUE();
Companion_instance_12 = new Companion_12();
Companion_instance_13 = new Companion_13();
Companion_instance_17 = new Companion_17();
Companion_instance_18 = new Companion_18();
Companion_instance_19 = new Companion_19();
Companion_instance_20 = new Companion_20();
ExceptionCodes_instance = new ExceptionCodes();
Companion_instance_21 = new Companion_21();
//endregion
//region block: exports
var KtList_0 = {};
KtList_0.fromJsArray = fromJsArray;
defineProp(F2Exception, 'Companion', Companion_getInstance_13, VOID, true);
ImPermission.values = values;
ImPermission.valueOf = valueOf;
defineProp(ImPermission, 'IM_FORCE_MFA_OTP', ImPermission_IM_FORCE_MFA_OTP_getInstance, VOID, true);
defineProp(ImPermission, 'IM_USER_READ', ImPermission_IM_USER_READ_getInstance, VOID, true);
defineProp(ImPermission, 'IM_USER_ROLE_WRITE', ImPermission_IM_USER_ROLE_WRITE_getInstance, VOID, true);
defineProp(ImPermission, 'IM_USER_WRITE', ImPermission_IM_USER_WRITE_getInstance, VOID, true);
defineProp(ImPermission, 'IM_MY_USER', ImPermission_IM_MY_USER_getInstance, VOID, true);
defineProp(ImPermission, 'IM_ORGANIZATION_READ', ImPermission_IM_ORGANIZATION_READ_getInstance, VOID, true);
defineProp(ImPermission, 'IM_ORGANIZATION_WRITE', ImPermission_IM_ORGANIZATION_WRITE_getInstance, VOID, true);
defineProp(ImPermission, 'IM_MY_ORGANIZATION_WRITE', ImPermission_IM_MY_ORGANIZATION_WRITE_getInstance, VOID, true);
defineProp(ImPermission, 'IM_MY_ORGANIZATION_READ', ImPermission_IM_MY_ORGANIZATION_READ_getInstance, VOID, true);
defineProp(ImPermission, 'IM_ORGANIZATION_API_KEY_READ', ImPermission_IM_ORGANIZATION_API_KEY_READ_getInstance, VOID, true);
defineProp(ImPermission, 'IM_ORGANIZATION_STATUS_WRITE', ImPermission_IM_ORGANIZATION_STATUS_WRITE_getInstance, VOID, true);
defineProp(ImPermission, 'IM_APIKEY_READ', ImPermission_IM_APIKEY_READ_getInstance, VOID, true);
defineProp(ImPermission, 'IM_APIKEY_WRITE', ImPermission_IM_APIKEY_WRITE_getInstance, VOID, true);
defineProp(ImPermission, 'IM_SPACE_READ', ImPermission_IM_SPACE_READ_getInstance, VOID, true);
defineProp(ImPermission, 'IM_SPACE_WRITE', ImPermission_IM_SPACE_WRITE_getInstance, VOID, true);
defineProp(ImPermission, 'IM_ROLE_READ', ImPermission_IM_ROLE_READ_getInstance, VOID, true);
defineProp(ImPermission, 'IM_ROLE_WRITE', ImPermission_IM_ROLE_WRITE_getInstance, VOID, true);
ExceptionCodes.getInstance = ExceptionCodes_getInstance;
export {
  KtList_0 as KtList,
  Envelope as Envelope,
  F2Error as F2Error,
  F2Exception as F2Exception,
  Page as Page,
  PageQuery as PageQuery,
  PageQueryResult as PageQueryResult,
  OffsetPagination as OffsetPagination,
  PagePagination as PagePagination,
  AuthRealm as AuthRealm,
  AuthRealmPassword as AuthRealmPassword,
  AuthRealmClientSecret as AuthRealmClientSecret,
  TokenInfo as TokenInfo,
  ImPermission as ImPermission,
  ExceptionCodes as ExceptionCodes,
  Address as Address,
};
//endregion

//# sourceMappingURL=connect-im-im-commons-im-commons-domain.mjs.map
