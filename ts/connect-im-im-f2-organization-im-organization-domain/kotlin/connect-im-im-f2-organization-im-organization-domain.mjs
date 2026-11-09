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
class Companion_0 {
  e1(map) {
    // Inline function 'kotlin.js.collections.toMap' call
    return createMapFrom(map);
  }
}
class KtMap {}
function asJsReadonlyMapView() {
  return createJsReadonlyMapViewFrom(this);
}
class Companion_1 {}
class Enum {
  constructor(name, ordinal) {
    this.l1_1 = name;
    this.m1_1 = ordinal;
  }
  n1() {
    return this.l1_1;
  }
  o1() {
    return this.m1_1;
  }
  p1(other) {
    return compareTo(this.m1_1, other.m1_1);
  }
  q1(other) {
    return this.p1(other instanceof Enum ? other : THROW_CCE());
  }
  equals(other) {
    return this === other;
  }
  hashCode() {
    return identityHashCode(this);
  }
  toString() {
    return this.l1_1;
  }
}
class Long extends Number_0 {
  toString() {
    return toStringImpl(this, 10);
  }
}
class FunctionAdapter {}
class arrayIterator$1 {
  constructor($array) {
    this.t1_1 = $array;
    this.s1_1 = 0;
  }
  g() {
    return !(this.s1_1 === this.t1_1.length);
  }
  h() {
    var tmp;
    if (!(this.s1_1 === this.t1_1.length)) {
      var _unary__edvuaz = this.s1_1;
      this.s1_1 = _unary__edvuaz + 1 | 0;
      tmp = this.t1_1[_unary__edvuaz];
    } else {
      throw NoSuchElementException.x1('' + this.s1_1);
    }
    return tmp;
  }
}
class JsArrayView extends Array {}
class JsMapView extends Map {}
class StringCompanionObject {}
class Digit {
  constructor() {
    Digit_instance = this;
    var tmp = this;
    // Inline function 'kotlin.intArrayOf' call
    tmp.c3_1 = new Int32Array([48, 1632, 1776, 1984, 2406, 2534, 2662, 2790, 2918, 3046, 3174, 3302, 3430, 3558, 3664, 3792, 3872, 4160, 4240, 6112, 6160, 6470, 6608, 6784, 6800, 6992, 7088, 7232, 7248, 42528, 43216, 43264, 43472, 43504, 43600, 44016, 65296]);
  }
}
class Comparator {}
class Unit {
  toString() {
    return 'kotlin.Unit';
  }
}
class AbstractCollection {
  static e3($box) {
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
  static d3() {
    return this.e3();
  }
  toJSON() {
    return this.toArray();
  }
  f3() {
  }
}
class IteratorImpl {
  constructor($outer, $box) {
    boxApply(this, $box);
    this.i3_1 = $outer;
    this.g3_1 = 0;
    this.h3_1 = -1;
  }
  g() {
    return this.g3_1 < this.i3_1.i();
  }
  h() {
    if (!this.g())
      throw NoSuchElementException.j3();
    var tmp = this;
    var _unary__edvuaz = this.g3_1;
    this.g3_1 = _unary__edvuaz + 1 | 0;
    tmp.h3_1 = _unary__edvuaz;
    return this.i3_1.w(this.h3_1);
  }
}
class ListIteratorImpl extends IteratorImpl {
  constructor($outer, index, $box) {
    if ($box === VOID)
      $box = {};
    $box.n3_1 = $outer;
    super($outer, $box);
    Companion_instance_4.p3(index, this.n3_1.i());
    this.g3_1 = index;
  }
  q3() {
    return this.g3_1 > 0;
  }
  r3() {
    return this.g3_1;
  }
  s3() {
    if (!this.q3())
      throw NoSuchElementException.j3();
    var tmp = this;
    this.g3_1 = this.g3_1 - 1 | 0;
    tmp.h3_1 = this.g3_1;
    return this.n3_1.w(this.h3_1);
  }
}
class AbstractMutableList extends AbstractMutableCollection {
  static z3() {
    var $this = this.d3();
    $this.y3_1 = 0;
    return $this;
  }
  o(element) {
    this.f3();
    this.b4(this.i(), element);
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
      while (iterator.q3()) {
        var it = iterator.s3();
        if (equals(it, element)) {
          tmp$ret$0 = iterator.r3();
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
    return SubList.x3(this, fromIndex, toIndex);
  }
  equals(other) {
    if (other === this)
      return true;
    if (!(!(other == null) ? isInterface(other, KtList) : false))
      return false;
    return Companion_instance_4.d4(this, other);
  }
  hashCode() {
    return Companion_instance_4.e4(this);
  }
}
class SubList extends AbstractMutableList {
  constructor(list, fromIndex, toIndex) {
    return new.target.x3(list, fromIndex, toIndex);
  }
  static x3(list, fromIndex, toIndex) {
    var $this = this.z3();
    $this.u3_1 = list;
    $this.v3_1 = fromIndex;
    $this.w3_1 = 0;
    Companion_instance_4.a4($this.v3_1, toIndex, $this.u3_1.i());
    $this.w3_1 = toIndex - $this.v3_1 | 0;
    return $this;
  }
  b4(index, element) {
    Companion_instance_4.p3(index, this.w3_1);
    this.u3_1.b4(this.v3_1 + index | 0, element);
    this.w3_1 = this.w3_1 + 1 | 0;
  }
  w(index) {
    Companion_instance_4.c4(index, this.w3_1);
    return this.u3_1.w(this.v3_1 + index | 0);
  }
  i() {
    return this.w3_1;
  }
  f3() {
    return this.u3_1.f3();
  }
}
class AbstractMap {
  static m4() {
    var $this = createThis(this);
    $this.k4_1 = null;
    $this.l4_1 = null;
    return $this;
  }
  f1(key) {
    return !(implFindEntry(this, key) == null);
  }
  g1(value) {
    var tmp0 = this.k1();
    var tmp$ret$0;
    $l$block_0: {
      // Inline function 'kotlin.collections.any' call
      var tmp;
      if (isInterface(tmp0, Collection)) {
        tmp = tmp0.s();
      } else {
        tmp = false;
      }
      if (tmp) {
        tmp$ret$0 = false;
        break $l$block_0;
      }
      var _iterator__ex2g4s = tmp0.f();
      while (_iterator__ex2g4s.g()) {
        var element = _iterator__ex2g4s.h();
        if (equals(element.d1(), value)) {
          tmp$ret$0 = true;
          break $l$block_0;
        }
      }
      tmp$ret$0 = false;
    }
    return tmp$ret$0;
  }
  t4(entry) {
    if (!(!(entry == null) ? isInterface(entry, Entry) : false))
      return false;
    var key = entry.c1();
    var value = entry.d1();
    // Inline function 'kotlin.collections.get' call
    var ourValue = (isInterface(this, KtMap) ? this : THROW_CCE()).h1(key);
    if (!equals(value, ourValue)) {
      return false;
    }
    var tmp;
    if (ourValue == null) {
      // Inline function 'kotlin.collections.containsKey' call
      tmp = !(isInterface(this, KtMap) ? this : THROW_CCE()).f1(key);
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
    var tmp0 = other.k1();
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
        if (!this.t4(element)) {
          tmp$ret$0 = false;
          break $l$block_0;
        }
      }
      tmp$ret$0 = true;
    }
    return tmp$ret$0;
  }
  h1(key) {
    var tmp0_safe_receiver = implFindEntry(this, key);
    return tmp0_safe_receiver == null ? null : tmp0_safe_receiver.d1();
  }
  hashCode() {
    return hashCode(this.k1());
  }
  s() {
    return this.i() === 0;
  }
  i() {
    return this.k1().i();
  }
  i1() {
    if (this.k4_1 == null) {
      var tmp = this;
      tmp.k4_1 = AbstractMap$keys$1.jc(this);
    }
    return ensureNotNull(this.k4_1);
  }
  toString() {
    var tmp = this.k1();
    return joinToString_1(tmp, ', ', '{', '}', VOID, VOID, AbstractMap$toString$lambda(this));
  }
  j1() {
    if (this.l4_1 == null) {
      var tmp = this;
      tmp.l4_1 = AbstractMap$values$1.mc(this);
    }
    return ensureNotNull(this.l4_1);
  }
}
class AbstractMutableMap extends AbstractMap {
  static j4() {
    var $this = this.m4();
    $this.h4_1 = null;
    $this.i4_1 = null;
    return $this;
  }
  n4() {
    return HashMapKeysDefault.p4(this);
  }
  q4() {
    return HashMapValuesDefault.s4(this);
  }
  i1() {
    var tmp0_elvis_lhs = this.h4_1;
    var tmp;
    if (tmp0_elvis_lhs == null) {
      // Inline function 'kotlin.also' call
      var this_0 = this.n4();
      this.h4_1 = this_0;
      tmp = this_0;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    return tmp;
  }
  j1() {
    var tmp0_elvis_lhs = this.i4_1;
    var tmp;
    if (tmp0_elvis_lhs == null) {
      // Inline function 'kotlin.also' call
      var this_0 = this.q4();
      this.i4_1 = this_0;
      tmp = this_0;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    return tmp;
  }
}
class AbstractMutableSet extends AbstractMutableCollection {
  static u4() {
    return this.d3();
  }
  equals(other) {
    if (other === this)
      return true;
    if (!(!(other == null) ? isInterface(other, KtSet) : false))
      return false;
    return Companion_instance_6.v4(this, other);
  }
  hashCode() {
    return Companion_instance_6.w4(this);
  }
}
class Companion_2 {
  constructor() {
    Companion_instance_2 = this;
    var tmp = this;
    // Inline function 'kotlin.also' call
    var this_0 = ArrayList.m(0);
    this_0.l_1 = true;
    tmp.x4_1 = this_0;
  }
}
class ArrayList extends AbstractMutableList {
  constructor(array) {
    return new.target.y1(array);
  }
  static y1(array) {
    Companion_getInstance_2();
    var $this = this.z3();
    $this.k_1 = array;
    $this.l_1 = false;
    return $this;
  }
  static y4() {
    Companion_getInstance_2();
    // Inline function 'kotlin.emptyArray' call
    var tmp$ret$0 = [];
    return this.y1(tmp$ret$0);
  }
  static m(initialCapacity) {
    Companion_getInstance_2();
    // Inline function 'kotlin.emptyArray' call
    var tmp$ret$0 = [];
    var $this = this.y1(tmp$ret$0);
    // Inline function 'kotlin.require' call
    if (!(initialCapacity >= 0)) {
      var message = 'Negative initial capacity: ' + initialCapacity;
      throw IllegalArgumentException.b3(toString_1(message));
    }
    return $this;
  }
  z1() {
    this.f3();
    this.l_1 = true;
    return this.i() > 0 ? this : Companion_getInstance_2().x4_1;
  }
  i() {
    return this.k_1.length;
  }
  w(index) {
    return this.k_1[rangeCheck(this, index)];
  }
  o(element) {
    this.f3();
    // Inline function 'kotlin.js.asDynamic' call
    this.k_1.push(element);
    this.y3_1 = this.y3_1 + 1 | 0;
    return true;
  }
  b4(index, element) {
    this.f3();
    // Inline function 'kotlin.js.asDynamic' call
    this.k_1.splice(insertionRangeCheck(this, index), 0, element);
    this.y3_1 = this.y3_1 + 1 | 0;
  }
  n(elements) {
    this.f3();
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
    this.y3_1 = this.y3_1 + 1 | 0;
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
  z4() {
    return [].slice.call(this.k_1);
  }
  toArray() {
    return this.z4();
  }
  f3() {
    if (this.l_1)
      throw UnsupportedOperationException.k2();
  }
}
class HashMap extends AbstractMutableMap {
  static g5(internalMap) {
    var $this = this.j4();
    init_kotlin_collections_HashMap($this);
    $this.e5_1 = internalMap;
    return $this;
  }
  static h5() {
    return this.g5(InternalHashMap.s5());
  }
  static t5(initialCapacity, loadFactor) {
    return this.g5(InternalHashMap.u5(initialCapacity, loadFactor));
  }
  static v5(initialCapacity) {
    return this.t5(initialCapacity, 1.0);
  }
  f1(key) {
    return this.e5_1.w5(key);
  }
  g1(value) {
    return this.e5_1.g1(value);
  }
  n4() {
    return HashMapKeys.y5(this.e5_1);
  }
  q4() {
    return HashMapValues.a6(this.e5_1);
  }
  k1() {
    var tmp0_elvis_lhs = this.f5_1;
    var tmp;
    if (tmp0_elvis_lhs == null) {
      // Inline function 'kotlin.also' call
      var this_0 = HashMapEntrySet.c6(this.e5_1);
      this.f5_1 = this_0;
      tmp = this_0;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    return tmp;
  }
  h1(key) {
    return this.e5_1.h1(key);
  }
  l2(key, value) {
    return this.e5_1.l2(key, value);
  }
  i() {
    return this.e5_1.i();
  }
}
class HashMapKeys extends AbstractMutableSet {
  constructor(backing) {
    return new.target.y5(backing);
  }
  static y5(backing) {
    var $this = this.u4();
    $this.x5_1 = backing;
    return $this;
  }
  i() {
    return this.x5_1.i();
  }
  s() {
    return this.x5_1.i() === 0;
  }
  t(element) {
    return this.x5_1.w5(element);
  }
  o(element) {
    throw UnsupportedOperationException.k2();
  }
  f() {
    return this.x5_1.d6();
  }
}
class HashMapValues extends AbstractMutableCollection {
  constructor(backing) {
    return new.target.a6(backing);
  }
  static a6(backing) {
    var $this = this.d3();
    $this.z5_1 = backing;
    return $this;
  }
  i() {
    return this.z5_1.i();
  }
  s() {
    return this.z5_1.i() === 0;
  }
  e6(element) {
    return this.z5_1.g1(element);
  }
  t(element) {
    if (!true)
      return false;
    return this.e6(element);
  }
  f6(element) {
    throw UnsupportedOperationException.k2();
  }
  o(element) {
    return this.f6(element);
  }
  f() {
    return this.z5_1.g6();
  }
}
class HashMapEntrySetBase extends AbstractMutableSet {
  static i6(backing) {
    var $this = this.u4();
    $this.h6_1 = backing;
    return $this;
  }
  i() {
    return this.h6_1.i();
  }
  s() {
    return this.h6_1.i() === 0;
  }
  k6(element) {
    return this.h6_1.m6(element);
  }
  t(element) {
    if (!(!(element == null) ? isInterface(element, Entry) : false))
      return false;
    return this.k6((!(element == null) ? isInterface(element, Entry) : false) ? element : THROW_CCE());
  }
  l6(element) {
    throw UnsupportedOperationException.k2();
  }
  o(element) {
    return this.l6((!(element == null) ? isInterface(element, Entry) : false) ? element : THROW_CCE());
  }
  u(elements) {
    return this.h6_1.n6(elements);
  }
}
class HashMapEntrySet extends HashMapEntrySetBase {
  constructor(backing) {
    return new.target.c6(backing);
  }
  static c6(backing) {
    return this.i6(backing);
  }
  f() {
    return this.h6_1.j6();
  }
}
class HashMapKeysDefault$iterator$1 {
  constructor($entryIterator) {
    this.o6_1 = $entryIterator;
  }
  g() {
    return this.o6_1.g();
  }
  h() {
    return this.o6_1.h().c1();
  }
}
class HashMapKeysDefault extends AbstractMutableSet {
  constructor(backingMap) {
    return new.target.p4(backingMap);
  }
  static p4(backingMap) {
    var $this = this.u4();
    $this.o4_1 = backingMap;
    return $this;
  }
  p6(element) {
    throw UnsupportedOperationException.q6('Add is not supported on keys');
  }
  o(element) {
    return this.p6(element);
  }
  w5(element) {
    return this.o4_1.f1(element);
  }
  t(element) {
    if (!true)
      return false;
    return this.w5(element);
  }
  f() {
    var entryIterator = this.o4_1.k1().f();
    return new HashMapKeysDefault$iterator$1(entryIterator);
  }
  i() {
    return this.o4_1.i();
  }
}
class HashMapValuesDefault$iterator$1 {
  constructor($entryIterator) {
    this.r6_1 = $entryIterator;
  }
  g() {
    return this.r6_1.g();
  }
  h() {
    return this.r6_1.h().d1();
  }
}
class HashMapValuesDefault extends AbstractMutableCollection {
  constructor(backingMap) {
    return new.target.s4(backingMap);
  }
  static s4(backingMap) {
    var $this = this.d3();
    $this.r4_1 = backingMap;
    return $this;
  }
  f6(element) {
    throw UnsupportedOperationException.q6('Add is not supported on values');
  }
  o(element) {
    return this.f6(element);
  }
  e6(element) {
    return this.r4_1.g1(element);
  }
  t(element) {
    if (!true)
      return false;
    return this.e6(element);
  }
  f() {
    var entryIterator = this.r4_1.k1().f();
    return new HashMapValuesDefault$iterator$1(entryIterator);
  }
  i() {
    return this.r4_1.i();
  }
}
class HashSet extends AbstractMutableSet {
  static s6(map) {
    var $this = this.u4();
    init_kotlin_collections_HashSet($this);
    $this.p_1 = map;
    return $this;
  }
  static t6() {
    return this.s6(InternalHashMap.s5());
  }
  static u6(initialCapacity, loadFactor) {
    return this.s6(InternalHashMap.u5(initialCapacity, loadFactor));
  }
  static q(initialCapacity) {
    return this.u6(initialCapacity, 1.0);
  }
  o(element) {
    return this.p_1.l2(element, true) == null;
  }
  t(element) {
    return this.p_1.w5(element);
  }
  s() {
    return this.p_1.i() === 0;
  }
  f() {
    return this.p_1.d6();
  }
  i() {
    return this.p_1.i();
  }
}
class Companion_3 {
  constructor() {
    this.h7_1 = -1640531527;
    this.i7_1 = 8;
    this.j7_1 = 2;
    this.k7_1 = -1;
  }
}
class Itr {
  constructor(map) {
    this.l7_1 = map;
    this.m7_1 = 0;
    this.n7_1 = -1;
    this.o7_1 = this.l7_1.p5_1;
    this.p7();
  }
  p7() {
    while (this.m7_1 < this.l7_1.n5_1 && this.l7_1.k5_1[this.m7_1] < 0) {
      this.m7_1 = this.m7_1 + 1 | 0;
    }
  }
  g() {
    return this.m7_1 < this.l7_1.n5_1;
  }
  q7() {
    if (!(this.l7_1.p5_1 === this.o7_1))
      throw ConcurrentModificationException.r7();
  }
}
class KeysItr extends Itr {
  h() {
    this.q7();
    if (this.m7_1 >= this.l7_1.n5_1)
      throw NoSuchElementException.j3();
    var tmp = this;
    var _unary__edvuaz = this.m7_1;
    this.m7_1 = _unary__edvuaz + 1 | 0;
    tmp.n7_1 = _unary__edvuaz;
    var result = this.l7_1.i5_1[this.n7_1];
    this.p7();
    return result;
  }
}
class ValuesItr extends Itr {
  h() {
    this.q7();
    if (this.m7_1 >= this.l7_1.n5_1)
      throw NoSuchElementException.j3();
    var tmp = this;
    var _unary__edvuaz = this.m7_1;
    this.m7_1 = _unary__edvuaz + 1 | 0;
    tmp.n7_1 = _unary__edvuaz;
    var result = ensureNotNull(this.l7_1.j5_1)[this.n7_1];
    this.p7();
    return result;
  }
}
class EntriesItr extends Itr {
  h() {
    this.q7();
    if (this.m7_1 >= this.l7_1.n5_1)
      throw NoSuchElementException.j3();
    var tmp = this;
    var _unary__edvuaz = this.m7_1;
    this.m7_1 = _unary__edvuaz + 1 | 0;
    tmp.n7_1 = _unary__edvuaz;
    var result = new EntryRef(this.l7_1, this.n7_1);
    this.p7();
    return result;
  }
  e8() {
    if (this.m7_1 >= this.l7_1.n5_1)
      throw NoSuchElementException.j3();
    var tmp = this;
    var _unary__edvuaz = this.m7_1;
    this.m7_1 = _unary__edvuaz + 1 | 0;
    tmp.n7_1 = _unary__edvuaz;
    // Inline function 'kotlin.hashCode' call
    var tmp0_safe_receiver = this.l7_1.i5_1[this.n7_1];
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : hashCode(tmp0_safe_receiver);
    var tmp_0 = tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
    // Inline function 'kotlin.hashCode' call
    var tmp0_safe_receiver_0 = ensureNotNull(this.l7_1.j5_1)[this.n7_1];
    var tmp1_elvis_lhs_0 = tmp0_safe_receiver_0 == null ? null : hashCode(tmp0_safe_receiver_0);
    var result = tmp_0 ^ (tmp1_elvis_lhs_0 == null ? 0 : tmp1_elvis_lhs_0);
    this.p7();
    return result;
  }
  f8(sb) {
    if (this.m7_1 >= this.l7_1.n5_1)
      throw NoSuchElementException.j3();
    var tmp = this;
    var _unary__edvuaz = this.m7_1;
    this.m7_1 = _unary__edvuaz + 1 | 0;
    tmp.n7_1 = _unary__edvuaz;
    var key = this.l7_1.i5_1[this.n7_1];
    if (equals(key, this.l7_1))
      sb.h8('(this Map)');
    else
      sb.g8(key);
    sb.i8(_Char___init__impl__6a9atx(61));
    var value = ensureNotNull(this.l7_1.j5_1)[this.n7_1];
    if (equals(value, this.l7_1))
      sb.h8('(this Map)');
    else
      sb.g8(value);
    this.p7();
  }
}
class EntryRef {
  constructor(map, index) {
    this.z6_1 = map;
    this.a7_1 = index;
    this.b7_1 = this.z6_1.p5_1;
  }
  c1() {
    checkForComodification(this);
    return this.z6_1.i5_1[this.a7_1];
  }
  d1() {
    checkForComodification(this);
    return ensureNotNull(this.z6_1.j5_1)[this.a7_1];
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
        tmp_0 = this.n8(entry);
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
    return new.target.j8(keysArray, valuesArray, presenceArray, hashArray, maxProbeDistance, length);
  }
  static j8(keysArray, valuesArray, presenceArray, hashArray, maxProbeDistance, length) {
    var $this = createThis(this);
    $this.i5_1 = keysArray;
    $this.j5_1 = valuesArray;
    $this.k5_1 = presenceArray;
    $this.l5_1 = hashArray;
    $this.m5_1 = maxProbeDistance;
    $this.n5_1 = length;
    $this.o5_1 = computeShift(Companion_instance_3, _get_hashSize__tftcho($this));
    $this.p5_1 = 0;
    $this.q5_1 = 0;
    $this.r5_1 = false;
    return $this;
  }
  i() {
    return this.q5_1;
  }
  static s5() {
    return this.k8(8);
  }
  static k8(initialCapacity) {
    return this.j8(arrayOfUninitializedElements(initialCapacity), null, new Int32Array(initialCapacity), new Int32Array(computeHashSize(Companion_instance_3, initialCapacity)), 2, 0);
  }
  static u5(initialCapacity, loadFactor) {
    var $this = this.k8(initialCapacity);
    // Inline function 'kotlin.require' call
    if (!(loadFactor > 0)) {
      var message = 'Non-positive load factor: ' + loadFactor;
      throw IllegalArgumentException.b3(toString_1(message));
    }
    return $this;
  }
  l8() {
    this.g7();
    this.r5_1 = true;
  }
  g1(value) {
    return findValue(this, value) >= 0;
  }
  h1(key) {
    var index = findKey(this, key);
    if (index < 0)
      return null;
    return ensureNotNull(this.j5_1)[index];
  }
  w5(key) {
    return findKey(this, key) >= 0;
  }
  l2(key, value) {
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
    var it = this.j6();
    while (it.g()) {
      result = result + it.e8() | 0;
    }
    return result;
  }
  toString() {
    var sb = StringBuilder.m8(2 + imul_0(this.q5_1, 3) | 0);
    sb.h8('{');
    var i = 0;
    var it = this.j6();
    while (it.g()) {
      if (i > 0) {
        sb.h8(', ');
      }
      it.f8(sb);
      i = i + 1 | 0;
    }
    sb.h8('}');
    return sb.toString();
  }
  g7() {
    if (this.r5_1)
      throw UnsupportedOperationException.k2();
  }
  m6(entry) {
    var index = findKey(this, entry.c1());
    if (index < 0)
      return false;
    return equals(ensureNotNull(this.j5_1)[index], entry.d1());
  }
  n8(entry) {
    return this.m6(isInterface(entry, Entry) ? entry : THROW_CCE());
  }
  d6() {
    return new KeysItr(this);
  }
  g6() {
    return new ValuesItr(this);
  }
  j6() {
    return new EntriesItr(this);
  }
}
class EmptyHolder {
  constructor() {
    EmptyHolder_instance = this;
    var tmp = this;
    // Inline function 'kotlin.also' call
    var this_0 = InternalHashMap.k8(0);
    this_0.l8();
    tmp.o8_1 = LinkedHashMap.p8(this_0);
  }
}
class LinkedHashMap extends HashMap {
  static g2() {
    var $this = this.h5();
    init_kotlin_collections_LinkedHashMap($this);
    return $this;
  }
  static q8(initialCapacity) {
    var $this = this.v5(initialCapacity);
    init_kotlin_collections_LinkedHashMap($this);
    return $this;
  }
  static p8(internalMap) {
    var $this = this.g5(internalMap);
    init_kotlin_collections_LinkedHashMap($this);
    return $this;
  }
  z1() {
    this.e5_1.l8();
    var tmp;
    if (this.i() > 0) {
      tmp = this;
    } else {
      // Inline function 'kotlin.js.unsafeCast' call
      // Inline function 'kotlin.js.asDynamic' call
      tmp = EmptyHolder_getInstance().o8_1;
    }
    return tmp;
  }
}
class Continuation {}
class CompletedContinuation {
  r8() {
    // Inline function 'kotlin.error' call
    var message = 'This continuation is already complete';
    throw IllegalStateException.p2(toString_1(message));
  }
  s8(result) {
    // Inline function 'kotlin.error' call
    var message = 'This continuation is already complete';
    throw IllegalStateException.p2(toString_1(message));
  }
  t8(result) {
    return this.s8(result);
  }
  toString() {
    return 'This continuation is already complete';
  }
}
class InterceptedCoroutine {
  constructor() {
    this.z8_1 = null;
  }
  b9() {
    var tmp0_elvis_lhs = this.z8_1;
    var tmp;
    if (tmp0_elvis_lhs == null) {
      var tmp1_safe_receiver = this.r8().c9(Key_instance);
      var tmp2_elvis_lhs = tmp1_safe_receiver == null ? null : tmp1_safe_receiver.d9(this);
      // Inline function 'kotlin.also' call
      var this_0 = tmp2_elvis_lhs == null ? this : tmp2_elvis_lhs;
      this.z8_1 = this_0;
      tmp = this_0;
    } else {
      tmp = tmp0_elvis_lhs;
    }
    return tmp;
  }
  a9() {
    var intercepted = this.z8_1;
    if (!(intercepted == null) && !(intercepted === this)) {
      ensureNotNull(this.r8().c9(Key_instance)).e9(intercepted);
    }
    this.z8_1 = CompletedContinuation_instance;
  }
}
class GeneratorCoroutineImpl extends InterceptedCoroutine {
  constructor(resultContinuation) {
    super();
    this.v8_1 = resultContinuation;
    var tmp = this;
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.w8_1 = VOID;
    var tmp_0 = this;
    var tmp0_safe_receiver = this.v8_1;
    tmp_0.x8_1 = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.r8();
  }
  r8() {
    return ensureNotNull(this.x8_1);
  }
  y8(result) {
    var suspended = get_COROUTINE_SUSPENDED();
    var e = Result__exceptionOrNull_impl_p6xea9(result);
    var stepResult = e == null ? this.w8_1.next(_Result___get_value__impl__bjfvqg(result)) : this.w8_1.throw(e);
    var done = stepResult.done;
    var value = stepResult.value;
    $l$loop_0: while (!done) {
      try {
        // Inline function 'kotlin.js.unsafeCast' call
        // Inline function 'kotlin.js.asDynamic' call
        value = value();
      } catch ($p) {
        var e_0 = $p;
        var nextStep = this.w8_1.throw(e_0);
        value = nextStep.value;
        done = nextStep.done;
        continue $l$loop_0;
      }
      if (value === suspended)
        break $l$loop_0;
      var nextStep_0 = this.w8_1.next(value);
      value = nextStep_0.value;
      done = nextStep_0.done;
    }
    return value;
  }
  s8(result) {
    var exception = null;
    var tmp;
    try {
      tmp = this.y8(result);
    } catch ($p) {
      var tmp_0;
      if ($p instanceof Error) {
        var e = $p;
        exception = e;
        tmp_0 = null;
      } else {
        throw $p;
      }
      tmp = tmp_0;
    }
    var nextResult = tmp;
    if (nextResult === get_COROUTINE_SUSPENDED())
      return Unit_instance;
    this.a9();
    var tmp0_safe_receiver = this.v8_1;
    if (tmp0_safe_receiver == null)
      null;
    else {
      // Inline function 'kotlin.run' call
      if (!(exception == null)) {
        // Inline function 'kotlin.coroutines.resumeWithException' call
        // Inline function 'kotlin.Companion.failure' call
        var exception_0 = exception;
        var tmp$ret$3 = _Result___init__impl__xyqfz8(createFailure(exception_0));
        tmp0_safe_receiver.t8(tmp$ret$3);
      } else {
        // Inline function 'kotlin.coroutines.resume' call
        // Inline function 'kotlin.Companion.success' call
        var tmp$ret$5 = _Result___init__impl__xyqfz8(nextResult);
        tmp0_safe_receiver.t8(tmp$ret$5);
      }
    }
  }
  t8(result) {
    return this.s8(result);
  }
}
class SafeContinuation {
  constructor(delegate, initialResult) {
    return new.target.h9(delegate, initialResult);
  }
  static h9(delegate, initialResult) {
    var $this = createThis(this);
    $this.f9_1 = delegate;
    $this.g9_1 = initialResult;
    return $this;
  }
  static i9(delegate) {
    return this.h9(delegate, CoroutineSingletons_UNDECIDED_getInstance());
  }
  r8() {
    return this.f9_1.r8();
  }
  t8(result) {
    var cur = this.g9_1;
    if (cur === CoroutineSingletons_UNDECIDED_getInstance()) {
      this.g9_1 = _Result___get_value__impl__bjfvqg(result);
    } else if (cur === get_COROUTINE_SUSPENDED()) {
      this.g9_1 = CoroutineSingletons_RESUMED_getInstance();
      this.f9_1.t8(result);
    } else
      throw IllegalStateException.p2('Already resumed');
  }
  j9() {
    if (this.g9_1 === CoroutineSingletons_UNDECIDED_getInstance()) {
      this.g9_1 = get_COROUTINE_SUSPENDED();
      return get_COROUTINE_SUSPENDED();
    }
    var result = this.g9_1;
    var tmp;
    if (result === CoroutineSingletons_RESUMED_getInstance()) {
      tmp = get_COROUTINE_SUSPENDED();
    } else {
      if (result instanceof Failure) {
        throw result.k9_1;
      } else {
        tmp = result;
      }
    }
    return tmp;
  }
}
class promisify$1$$inlined$Continuation$1 {
  constructor($context, $resolve, $reject) {
    this.p9_1 = $context;
    this.q9_1 = $resolve;
    this.r9_1 = $reject;
  }
  r8() {
    return this.p9_1;
  }
  s8(result) {
    // Inline function 'kotlin.onSuccess' call
    var action = this.q9_1;
    if (_Result___get_isSuccess__impl__sndoy8(result))
      action(_Result___get_value__impl__bjfvqg(result));
    // Inline function 'kotlin.onFailure' call
    var action_0 = this.r9_1;
    var tmp0_safe_receiver = Result__exceptionOrNull_impl_p6xea9(result);
    if (tmp0_safe_receiver == null)
      null;
    else {
      // Inline function 'kotlin.let' call
      action_0(tmp0_safe_receiver);
    }
    return Unit_instance;
  }
  t8(result) {
    return this.s8(result);
  }
}
class Exception extends Error {
  static z9() {
    var $this = createExternalThis(this, Error, [null]);
    setPropertiesToThrowableInstance($this);
    init_kotlin_Exception($this);
    return $this;
  }
  static aa(message) {
    var $this = createExternalThis(this, Error, [message]);
    setPropertiesToThrowableInstance($this, message);
    init_kotlin_Exception($this);
    return $this;
  }
  static ba(message, cause) {
    var $this = createExternalThis(this, Error, [message, setupCauseParameter(cause)]);
    setPropertiesToThrowableInstance($this, message, cause);
    init_kotlin_Exception($this);
    return $this;
  }
}
class RuntimeException extends Exception {
  static s9() {
    var $this = this.z9();
    init_kotlin_RuntimeException($this);
    return $this;
  }
  static e7(message) {
    var $this = this.aa(message);
    init_kotlin_RuntimeException($this);
    return $this;
  }
  static x9(message, cause) {
    var $this = this.ba(message, cause);
    init_kotlin_RuntimeException($this);
    return $this;
  }
}
class UnsupportedOperationException extends RuntimeException {
  static k2() {
    var $this = this.s9();
    init_kotlin_UnsupportedOperationException($this);
    return $this;
  }
  static q6(message) {
    var $this = this.e7(message);
    init_kotlin_UnsupportedOperationException($this);
    return $this;
  }
}
class IllegalArgumentException extends RuntimeException {
  static v9() {
    var $this = this.s9();
    init_kotlin_IllegalArgumentException($this);
    return $this;
  }
  static b3(message) {
    var $this = this.e7(message);
    init_kotlin_IllegalArgumentException($this);
    return $this;
  }
  static w9(message, cause) {
    var $this = this.x9(message, cause);
    init_kotlin_IllegalArgumentException($this);
    return $this;
  }
}
class NoSuchElementException extends RuntimeException {
  static j3() {
    var $this = this.s9();
    init_kotlin_NoSuchElementException($this);
    return $this;
  }
  static x1(message) {
    var $this = this.e7(message);
    init_kotlin_NoSuchElementException($this);
    return $this;
  }
}
class IndexOutOfBoundsException extends RuntimeException {
  static fa() {
    var $this = this.s9();
    init_kotlin_IndexOutOfBoundsException($this);
    return $this;
  }
  static ga(message) {
    var $this = this.e7(message);
    init_kotlin_IndexOutOfBoundsException($this);
    return $this;
  }
}
class IllegalStateException extends RuntimeException {
  static ha() {
    var $this = this.s9();
    init_kotlin_IllegalStateException($this);
    return $this;
  }
  static p2(message) {
    var $this = this.e7(message);
    init_kotlin_IllegalStateException($this);
    return $this;
  }
}
class ConcurrentModificationException extends RuntimeException {
  static r7() {
    var $this = this.s9();
    init_kotlin_ConcurrentModificationException($this);
    return $this;
  }
  static y6(message) {
    var $this = this.e7(message);
    init_kotlin_ConcurrentModificationException($this);
    return $this;
  }
}
class ArithmeticException extends RuntimeException {
  static la() {
    var $this = this.s9();
    init_kotlin_ArithmeticException($this);
    return $this;
  }
  static ma(message) {
    var $this = this.e7(message);
    init_kotlin_ArithmeticException($this);
    return $this;
  }
}
class Error_0 extends Error {
  static oa() {
    var $this = createExternalThis(this, Error, [null]);
    setPropertiesToThrowableInstance($this);
    init_kotlin_Error($this);
    return $this;
  }
  static pa(message) {
    var $this = createExternalThis(this, Error, [message]);
    setPropertiesToThrowableInstance($this, message);
    init_kotlin_Error($this);
    return $this;
  }
}
class NullPointerException extends RuntimeException {
  static t2() {
    var $this = this.s9();
    init_kotlin_NullPointerException($this);
    return $this;
  }
}
class ClassCastException extends RuntimeException {
  static x2() {
    var $this = this.s9();
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
        tmp = (equals(this.ua(), other.ua()) && this.qa() == other.qa());
      } else {
        tmp = false;
      }
    }
    return tmp;
  }
  hashCode() {
    var tmp0_safe_receiver = this.qa();
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : getStringHashCode(tmp0_safe_receiver);
    return tmp1_elvis_lhs == null ? 0 : tmp1_elvis_lhs;
  }
  toString() {
    return 'class ' + this.qa();
  }
}
class PrimitiveKClassImpl extends KClassImpl {
  constructor(jClass, simpleName, isInstanceFunction) {
    super();
    this.ra_1 = jClass;
    this.sa_1 = simpleName;
    this.ta_1 = isInstanceFunction;
  }
  ua() {
    return this.ra_1;
  }
  qa() {
    return this.sa_1;
  }
}
class NothingKClassImpl extends KClassImpl {
  constructor() {
    NothingKClassImpl_instance = null;
    super();
    NothingKClassImpl_instance = this;
    this.va_1 = 'Nothing';
  }
  qa() {
    return this.va_1;
  }
  ua() {
    throw UnsupportedOperationException.q6("There's no native JS class for Nothing type");
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
    this.wa_1 = jClass;
    var tmp = this;
    // Inline function 'kotlin.js.asDynamic' call
    var tmp0_safe_receiver = this.wa_1.$metadata$;
    // Inline function 'kotlin.js.unsafeCast' call
    tmp.xa_1 = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.simpleName;
  }
  ua() {
    return this.wa_1;
  }
  qa() {
    return this.xa_1;
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
  ya() {
    return this.anyClass;
  }
  za() {
    return this.numberClass;
  }
  ab() {
    return this.nothingClass;
  }
  bb() {
    return this.booleanClass;
  }
  cb() {
    return this.byteClass;
  }
  db() {
    return this.shortClass;
  }
  eb() {
    return this.intClass;
  }
  fb() {
    return this.longClass;
  }
  gb() {
    return this.floatClass;
  }
  hb() {
    return this.doubleClass;
  }
  ib() {
    return this.arrayClass;
  }
  jb() {
    return this.stringClass;
  }
  kb() {
    return this.throwableClass;
  }
  lb() {
    return this.booleanArrayClass;
  }
  mb() {
    return this.charArrayClass;
  }
  nb() {
    return this.byteArrayClass;
  }
  ob() {
    return this.shortArrayClass;
  }
  pb() {
    return this.intArrayClass;
  }
  qb() {
    return this.bigIntClass;
  }
  rb() {
    return this.longArrayClass;
  }
  sb() {
    return this.floatArrayClass;
  }
  tb() {
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
    return new.target.wb(message);
  }
  static wb(message) {
    var $this = this.aa(message);
    captureStack($this, $this.vb_1);
    return $this;
  }
  static xb() {
    return this.wb(null);
  }
}
class StringBuilder {
  constructor(content) {
    return new.target.yb(content);
  }
  static yb(content) {
    var $this = createThis(this);
    $this.c_1 = content;
    return $this;
  }
  static m8(capacity) {
    return this.d();
  }
  static d() {
    return this.yb('');
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
      throw IndexOutOfBoundsException.ga('index: ' + index + ', length: ' + this.a() + '}');
    }
    return tmp;
  }
  i8(value) {
    this.c_1 = this.c_1 + toString(value);
    return this;
  }
  e(value) {
    this.c_1 = this.c_1 + toString_0(value);
    return this;
  }
  g8(value) {
    this.c_1 = this.c_1 + toString_0(value);
    return this;
  }
  zb(value) {
    return this.h8(value.toString());
  }
  h8(value) {
    var tmp = this;
    var tmp_0 = this.c_1;
    tmp.c_1 = tmp_0 + (value == null ? 'null' : value);
    return this;
  }
  toString() {
    return this.c_1;
  }
  ac(index) {
    Companion_instance_4.c4(index, this.a());
    this.c_1 = substring(this.c_1, 0, index) + substring_0(this.c_1, index + 1 | 0);
    return this;
  }
}
class sam$kotlin_Comparator$0 {
  constructor(function_0) {
    this.bc_1 = function_0;
  }
  cc(a, b) {
    return this.bc_1(a, b);
  }
  compare(a, b) {
    return this.cc(a, b);
  }
  r1() {
    return this.bc_1;
  }
  equals(other) {
    var tmp;
    if (!(other == null) ? isInterface(other, Comparator) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.r1(), other.r1());
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
    return hashCode(this.r1());
  }
}
class Companion_4 {
  constructor() {
    this.o3_1 = 2147483639;
  }
  c4(index, size) {
    if (index < 0 || index >= size) {
      throw IndexOutOfBoundsException.ga('index: ' + index + ', size: ' + size);
    }
  }
  p3(index, size) {
    if (index < 0 || index > size) {
      throw IndexOutOfBoundsException.ga('index: ' + index + ', size: ' + size);
    }
  }
  a4(fromIndex, toIndex, size) {
    if (fromIndex < 0 || toIndex > size) {
      throw IndexOutOfBoundsException.ga('fromIndex: ' + fromIndex + ', toIndex: ' + toIndex + ', size: ' + size);
    }
    if (fromIndex > toIndex) {
      throw IllegalArgumentException.b3('fromIndex: ' + fromIndex + ' > toIndex: ' + toIndex);
    }
  }
  f7(oldCapacity, minCapacity) {
    var newCapacity = oldCapacity + (oldCapacity >> 1) | 0;
    if ((newCapacity - minCapacity | 0) < 0)
      newCapacity = minCapacity;
    if ((newCapacity - 2147483639 | 0) > 0)
      newCapacity = minCapacity > 2147483639 ? 2147483647 : 2147483639;
    return newCapacity;
  }
  e4(c) {
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
  d4(c, other) {
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
    this.gc_1 = $entryIterator;
  }
  g() {
    return this.gc_1.g();
  }
  h() {
    return this.gc_1.h().c1();
  }
}
class AbstractMap$values$1$iterator$1 {
  constructor($entryIterator) {
    this.hc_1 = $entryIterator;
  }
  g() {
    return this.hc_1.g();
  }
  h() {
    return this.hc_1.h().d1();
  }
}
class Companion_5 {}
class AbstractSet extends AbstractCollection {
  static kc($box) {
    return this.e3($box);
  }
  equals(other) {
    if (other === this)
      return true;
    if (!(!(other == null) ? isInterface(other, KtSet) : false))
      return false;
    return Companion_instance_6.v4(this, other);
  }
  hashCode() {
    return Companion_instance_6.w4(this);
  }
}
class AbstractMap$keys$1 extends AbstractSet {
  constructor(this$0) {
    return new.target.jc(this$0);
  }
  static jc(this$0, $box) {
    if ($box === VOID)
      $box = {};
    $box.ic_1 = this$0;
    return this.kc($box);
  }
  w5(element) {
    return this.ic_1.f1(element);
  }
  t(element) {
    if (!true)
      return false;
    return this.w5(element);
  }
  f() {
    var entryIterator = this.ic_1.k1().f();
    return new AbstractMap$keys$1$iterator$1(entryIterator);
  }
  i() {
    return this.ic_1.i();
  }
}
class AbstractMap$values$1 extends AbstractCollection {
  constructor(this$0) {
    return new.target.mc(this$0);
  }
  static mc(this$0, $box) {
    if ($box === VOID)
      $box = {};
    $box.lc_1 = this$0;
    return this.e3($box);
  }
  e6(element) {
    return this.lc_1.g1(element);
  }
  t(element) {
    if (!true)
      return false;
    return this.e6(element);
  }
  f() {
    var entryIterator = this.lc_1.k1().f();
    return new AbstractMap$values$1$iterator$1(entryIterator);
  }
  i() {
    return this.lc_1.i();
  }
}
class Companion_6 {
  w4(c) {
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
  v4(c, other) {
    if (!(c.i() === other.i()))
      return false;
    return c.u(other);
  }
}
class EmptyList {
  constructor() {
    this.nc_1 = -7390468764508069838n;
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
  oc(element) {
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
    return this.oc(tmp);
  }
  pc(elements) {
    return elements.s();
  }
  u(elements) {
    return this.pc(elements);
  }
  w(index) {
    throw IndexOutOfBoundsException.ga("Empty list doesn't contain element at index " + index + '.');
  }
  qc(element) {
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
    return this.qc(tmp);
  }
  rc(element) {
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
    return this.rc(tmp);
  }
  f() {
    return EmptyIterator_instance;
  }
  z() {
    return EmptyIterator_instance;
  }
  a1(index) {
    if (!(index === 0))
      throw IndexOutOfBoundsException.ga('Index: ' + index);
    return EmptyIterator_instance;
  }
  b1(fromIndex, toIndex) {
    if (fromIndex === 0 && toIndex === 0)
      return this;
    throw IndexOutOfBoundsException.ga('fromIndex: ' + fromIndex + ', toIndex: ' + toIndex);
  }
}
class EmptyIterator {
  g() {
    return false;
  }
  q3() {
    return false;
  }
  r3() {
    return 0;
  }
  h() {
    throw NoSuchElementException.j3();
  }
  s3() {
    throw NoSuchElementException.j3();
  }
}
class IndexedValue {
  constructor(index, value) {
    this.sc_1 = index;
    this.tc_1 = value;
  }
  toString() {
    return 'IndexedValue(index=' + this.sc_1 + ', value=' + toString_0(this.tc_1) + ')';
  }
  hashCode() {
    var result = this.sc_1;
    result = imul_0(result, 31) + (this.tc_1 == null ? 0 : hashCode(this.tc_1)) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof IndexedValue))
      return false;
    if (!(this.sc_1 === other.sc_1))
      return false;
    if (!equals(this.tc_1, other.tc_1))
      return false;
    return true;
  }
}
class IndexingIterable {
  constructor(iteratorFactory) {
    this.uc_1 = iteratorFactory;
  }
  f() {
    return new IndexingIterator(this.uc_1());
  }
}
class IndexingIterator {
  constructor(iterator) {
    this.vc_1 = iterator;
    this.wc_1 = 0;
  }
  g() {
    return this.vc_1.g();
  }
  h() {
    var _unary__edvuaz = this.wc_1;
    this.wc_1 = _unary__edvuaz + 1 | 0;
    return new IndexedValue(checkIndexOverflow(_unary__edvuaz), this.vc_1.h());
  }
}
class EmptyMap {
  constructor() {
    this.xc_1 = 8246714829545688274n;
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
  yc(key) {
    return false;
  }
  f1(key) {
    if (!true)
      return false;
    return this.yc(key);
  }
  zc(value) {
    return false;
  }
  g1(value) {
    if (!false)
      return false;
    var tmp;
    if (false) {
      tmp = value;
    } else {
      tmp = THROW_CCE();
    }
    return this.zc(tmp);
  }
  ad(key) {
    return null;
  }
  h1(key) {
    if (!true)
      return null;
    return this.ad(key);
  }
  k1() {
    return EmptySet_instance;
  }
  i1() {
    return EmptySet_instance;
  }
  j1() {
    return EmptyList_instance;
  }
}
class IntIterator {
  h() {
    return this.fd();
  }
}
class EmptySet {
  constructor() {
    this.gd_1 = 3406603774387020532n;
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
  pc(elements) {
    return elements.s();
  }
  u(elements) {
    return this.pc(elements);
  }
  f() {
    return EmptyIterator_instance;
  }
}
class Key {}
class ContinuationInterceptor {}
function releaseInterceptedContinuation(continuation) {
}
class EmptyCoroutineContext {
  constructor() {
    this.hd_1 = 0n;
  }
  c9(key) {
    return null;
  }
  hashCode() {
    return 0;
  }
  toString() {
    return 'EmptyCoroutineContext';
  }
}
class CoroutineSingletons extends Enum {}
class Companion_7 {
  constructor() {
    Companion_instance_7 = this;
    this.r_1 = new IntRange(1, 0);
  }
}
class IntProgression {
  constructor(start, endInclusive, step) {
    if (step === 0)
      throw IllegalArgumentException.b3('Step must be non-zero.');
    if (step === -2147483648)
      throw IllegalArgumentException.b3('Step must be greater than Int.MIN_VALUE to avoid overflow on negation.');
    this.ld_1 = start;
    this.md_1 = getProgressionLastElement(start, endInclusive, step);
    this.nd_1 = step;
  }
  f() {
    return new IntProgressionIterator(this.ld_1, this.md_1, this.nd_1);
  }
  s() {
    return this.nd_1 > 0 ? this.ld_1 > this.md_1 : this.ld_1 < this.md_1;
  }
  equals(other) {
    var tmp;
    if (other instanceof IntProgression) {
      tmp = this.s() && other.s() || (this.ld_1 === other.ld_1 && this.md_1 === other.md_1 && this.nd_1 === other.nd_1);
    } else {
      tmp = false;
    }
    return tmp;
  }
  hashCode() {
    return this.s() ? -1 : imul_0(31, imul_0(31, this.ld_1) + this.md_1 | 0) + this.nd_1 | 0;
  }
  toString() {
    return this.nd_1 > 0 ? '' + this.ld_1 + '..' + this.md_1 + ' step ' + this.nd_1 : '' + this.ld_1 + ' downTo ' + this.md_1 + ' step ' + (-this.nd_1 | 0);
  }
}
class IntRange extends IntProgression {
  constructor(start, endInclusive) {
    Companion_getInstance_7();
    super(start, endInclusive, 1);
  }
  s() {
    return this.ld_1 > this.md_1;
  }
  equals(other) {
    var tmp;
    if (other instanceof IntRange) {
      tmp = this.s() && other.s() || (this.ld_1 === other.ld_1 && this.md_1 === other.md_1);
    } else {
      tmp = false;
    }
    return tmp;
  }
  hashCode() {
    return this.s() ? -1 : imul_0(31, this.ld_1) + this.md_1 | 0;
  }
  toString() {
    return '' + this.ld_1 + '..' + this.md_1;
  }
}
class IntProgressionIterator extends IntIterator {
  constructor(first, last, step) {
    super();
    this.od_1 = step;
    this.pd_1 = last;
    this.qd_1 = this.od_1 > 0 ? first <= last : first >= last;
    this.rd_1 = this.qd_1 ? first : this.pd_1;
  }
  g() {
    return this.qd_1;
  }
  fd() {
    var value = this.rd_1;
    if (value === this.pd_1) {
      if (!this.qd_1)
        throw NoSuchElementException.j3();
      this.qd_1 = false;
    } else {
      this.rd_1 = this.rd_1 + this.od_1 | 0;
    }
    return value;
  }
}
class Companion_8 {}
class System {
  td() {
    return systemClockNow();
  }
}
class Companion_9 {
  constructor() {
    Companion_instance_9 = this;
    this.dc_1 = new Instant(-31557014167219200n, 0);
    this.ec_1 = new Instant(31556889864403199n, 999999999);
  }
  fc(epochMilliseconds) {
    // Inline function 'kotlin.floorDiv' call
    var q = divide(epochMilliseconds, 1000n);
    if ((epochMilliseconds ^ 1000n) < 0n && !(multiply_0(q, 1000n) === epochMilliseconds)) {
      var _unary__edvuaz = q;
      q = subtract_0(_unary__edvuaz, get_ONE());
    }
    var epochSeconds = q;
    // Inline function 'kotlin.mod' call
    var r = modulo(epochMilliseconds, 1000n);
    // Inline function 'kotlin.Long.times' call
    var this_0 = add_0(r, 1000n & shiftRight((r ^ 1000n) & (r | negate_0(r)), 63));
    var tmp$ret$2 = multiply_0(this_0, fromInt_0(1000000));
    var nanosecondsOfSecond = convertToInt(tmp$ret$2);
    return epochSeconds < -31557014167219200n ? this.dc_1 : epochSeconds > 31556889864403199n ? this.ec_1 : this.ud(epochSeconds, nanosecondsOfSecond);
  }
  vd(epochSeconds, nanosecondAdjustment) {
    // Inline function 'kotlin.floorDiv' call
    var other = 1000000000n;
    var q = divide(nanosecondAdjustment, other);
    if ((nanosecondAdjustment ^ other) < 0n && !(multiply_0(q, other) === nanosecondAdjustment)) {
      var _unary__edvuaz = q;
      q = subtract_0(_unary__edvuaz, get_ONE());
    }
    // Inline function 'kotlin.time.safeAddOrElse' call
    var b = q;
    var sum = add_0(epochSeconds, b);
    if ((epochSeconds ^ sum) < 0n && (epochSeconds ^ b) >= 0n) {
      return epochSeconds > 0n ? Companion_getInstance_9().ec_1 : Companion_getInstance_9().dc_1;
    }
    var seconds = sum;
    var tmp;
    if (seconds < -31557014167219200n) {
      tmp = this.dc_1;
    } else if (seconds > 31556889864403199n) {
      tmp = this.ec_1;
    } else {
      // Inline function 'kotlin.mod' call
      var other_0 = 1000000000n;
      var r = modulo(nanosecondAdjustment, other_0);
      var tmp$ret$3 = add_0(r, other_0 & shiftRight((r ^ other_0) & (r | negate_0(r)), 63));
      var nanoseconds = convertToInt(tmp$ret$3);
      tmp = new Instant(seconds, nanoseconds);
    }
    return tmp;
  }
  ud(epochSeconds, nanosecondAdjustment) {
    return this.vd(epochSeconds, fromInt_0(nanosecondAdjustment));
  }
}
class Instant {
  constructor(epochSeconds, nanosecondsOfSecond) {
    Companion_getInstance_9();
    this.wd_1 = epochSeconds;
    this.xd_1 = nanosecondsOfSecond;
    var containsArg = this.wd_1;
    // Inline function 'kotlin.require' call
    if (!(-31557014167219200n <= containsArg ? containsArg <= 31556889864403199n : false)) {
      var message = 'Instant exceeds minimum or maximum instant';
      throw IllegalArgumentException.b3(toString_1(message));
    }
  }
  yd(other) {
    var s = compareTo(this.wd_1, other.wd_1);
    if (!(s === 0)) {
      return s;
    }
    return compareTo(this.xd_1, other.xd_1);
  }
  q1(other) {
    return this.yd(other instanceof Instant ? other : THROW_CCE());
  }
  equals(other) {
    var tmp;
    if (this === other) {
      tmp = true;
    } else {
      var tmp_0;
      var tmp_1;
      if (other instanceof Instant) {
        tmp_1 = this.wd_1 === other.wd_1;
      } else {
        tmp_1 = false;
      }
      if (tmp_1) {
        tmp_0 = this.xd_1 === other.xd_1;
      } else {
        tmp_0 = false;
      }
      tmp = tmp_0;
    }
    return tmp;
  }
  hashCode() {
    return getBigIntHashCode(this.wd_1) + imul_0(51, this.xd_1) | 0;
  }
  toString() {
    return formatIso(this);
  }
}
class Companion_10 {
  zd(instant) {
    var localSecond = instant.wd_1;
    // Inline function 'kotlin.floorDiv' call
    var q = divide(localSecond, 86400n);
    if ((localSecond ^ 86400n) < 0n && !(multiply_0(q, 86400n) === localSecond)) {
      var _unary__edvuaz = q;
      q = subtract_0(_unary__edvuaz, get_ONE());
    }
    var epochDays = q;
    // Inline function 'kotlin.mod' call
    var r = modulo(localSecond, 86400n);
    var tmp$ret$1 = add_0(r, 86400n & shiftRight((r ^ 86400n) & (r | negate_0(r)), 63));
    var secsOfDay = convertToInt(tmp$ret$1);
    var year;
    var month;
    var day;
    // Inline function 'kotlin.run' call
    // Inline function 'kotlin.Long.plus' call
    var zeroDay = add_0(epochDays, fromInt_0(719528));
    // Inline function 'kotlin.Long.minus' call
    var this_0 = zeroDay;
    zeroDay = subtract_0(this_0, fromInt_0(60));
    var adjust = 0n;
    if (zeroDay < 0n) {
      // Inline function 'kotlin.Long.plus' call
      var this_1 = zeroDay;
      // Inline function 'kotlin.Long.div' call
      var this_2 = add_0(this_1, fromInt_0(1));
      // Inline function 'kotlin.Long.minus' call
      var this_3 = divide(this_2, fromInt_0(146097));
      var adjustCycles = subtract_0(this_3, fromInt_0(1));
      // Inline function 'kotlin.Long.times' call
      adjust = multiply_0(adjustCycles, fromInt_0(400));
      var tmp = zeroDay;
      // Inline function 'kotlin.Long.times' call
      var this_4 = negate_0(adjustCycles);
      var tmp$ret$10 = multiply_0(this_4, fromInt_0(146097));
      zeroDay = add_0(tmp, tmp$ret$10);
    }
    // Inline function 'kotlin.Long.plus' call
    var this_5 = multiply_0(numberToLong(400), zeroDay);
    // Inline function 'kotlin.Long.div' call
    var this_6 = add_0(this_5, fromInt_0(591));
    var yearEst = divide(this_6, fromInt_0(146097));
    var tmp_0 = zeroDay;
    var tmp_1 = multiply_0(numberToLong(365), yearEst);
    // Inline function 'kotlin.Long.div' call
    var this_7 = yearEst;
    var tmp$ret$13 = divide(this_7, fromInt_0(4));
    var tmp_2 = add_0(tmp_1, tmp$ret$13);
    // Inline function 'kotlin.Long.div' call
    var this_8 = yearEst;
    var tmp$ret$14 = divide(this_8, fromInt_0(100));
    var tmp_3 = subtract_0(tmp_2, tmp$ret$14);
    // Inline function 'kotlin.Long.div' call
    var this_9 = yearEst;
    var tmp$ret$15 = divide(this_9, fromInt_0(400));
    var doyEst = subtract_0(tmp_0, add_0(tmp_3, tmp$ret$15));
    if (doyEst < 0n) {
      var _unary__edvuaz_0 = yearEst;
      yearEst = subtract_0(_unary__edvuaz_0, get_ONE());
      var tmp_4 = zeroDay;
      var tmp_5 = multiply_0(numberToLong(365), yearEst);
      // Inline function 'kotlin.Long.div' call
      var this_10 = yearEst;
      var tmp$ret$16 = divide(this_10, fromInt_0(4));
      var tmp_6 = add_0(tmp_5, tmp$ret$16);
      // Inline function 'kotlin.Long.div' call
      var this_11 = yearEst;
      var tmp$ret$17 = divide(this_11, fromInt_0(100));
      var tmp_7 = subtract_0(tmp_6, tmp$ret$17);
      // Inline function 'kotlin.Long.div' call
      var this_12 = yearEst;
      var tmp$ret$18 = divide(this_12, fromInt_0(400));
      doyEst = subtract_0(tmp_4, add_0(tmp_7, tmp$ret$18));
    }
    yearEst = add_0(yearEst, adjust);
    var marchDoy0 = convertToInt(doyEst);
    var marchMonth0 = (imul_0(marchDoy0, 5) + 2 | 0) / 153 | 0;
    month = ((marchMonth0 + 2 | 0) % 12 | 0) + 1 | 0;
    day = (marchDoy0 - ((imul_0(marchMonth0, 306) + 5 | 0) / 10 | 0) | 0) + 1 | 0;
    var tmp0 = yearEst;
    // Inline function 'kotlin.Long.plus' call
    var other = marchMonth0 / 10 | 0;
    var tmp$ret$19 = add_0(tmp0, fromInt_0(other));
    year = convertToInt(tmp$ret$19);
    var hours = secsOfDay / 3600 | 0;
    var secondWithoutHours = secsOfDay - imul_0(hours, 3600) | 0;
    var minutes = secondWithoutHours / 60 | 0;
    var second = secondWithoutHours - imul_0(minutes, 60) | 0;
    return new UnboundLocalDateTime(year, month, day, hours, minutes, second, instant.xd_1);
  }
}
class UnboundLocalDateTime {
  constructor(year, month, day, hour, minute, second, nanosecond) {
    this.ae_1 = year;
    this.be_1 = month;
    this.ce_1 = day;
    this.de_1 = hour;
    this.ee_1 = minute;
    this.fe_1 = second;
    this.ge_1 = nanosecond;
  }
  toString() {
    return 'UnboundLocalDateTime(' + this.ae_1 + '-' + this.be_1 + '-' + this.ce_1 + ' ' + this.de_1 + ':' + this.ee_1 + ':' + this.fe_1 + '.' + this.ge_1 + ')';
  }
}
class LazyThreadSafetyMode extends Enum {}
class UnsafeLazyImpl {
  constructor(initializer) {
    this.he_1 = initializer;
    this.ie_1 = UNINITIALIZED_VALUE_instance;
  }
  d1() {
    if (this.ie_1 === UNINITIALIZED_VALUE_instance) {
      this.ie_1 = ensureNotNull(this.he_1)();
      this.he_1 = null;
    }
    return this.ie_1;
  }
  je() {
    return !(this.ie_1 === UNINITIALIZED_VALUE_instance);
  }
  toString() {
    return this.je() ? toString_0(this.d1()) : 'Lazy value not initialized yet.';
  }
}
class UNINITIALIZED_VALUE {}
class Companion_11 {}
class Failure {
  constructor(exception) {
    this.k9_1 = exception;
  }
  equals(other) {
    var tmp;
    if (other instanceof Failure) {
      tmp = equals(this.k9_1, other.k9_1);
    } else {
      tmp = false;
    }
    return tmp;
  }
  hashCode() {
    return hashCode(this.k9_1);
  }
  toString() {
    return 'Failure(' + this.k9_1.toString() + ')';
  }
}
class NotImplementedError extends Error_0 {
  constructor(message) {
    return new.target.n9(message);
  }
  static n9(message) {
    message = message === VOID ? 'An operation is not implemented.' : message;
    var $this = this.pa(message);
    captureStack($this, $this.m9_1);
    return $this;
  }
}
class Pair {
  constructor(first, second) {
    this.bd_1 = first;
    this.cd_1 = second;
  }
  toString() {
    return '(' + toString_0(this.bd_1) + ', ' + toString_0(this.cd_1) + ')';
  }
  dd() {
    return this.bd_1;
  }
  ed() {
    return this.cd_1;
  }
  hashCode() {
    var result = this.bd_1 == null ? 0 : hashCode(this.bd_1);
    result = imul_0(result, 31) + (this.cd_1 == null ? 0 : hashCode(this.cd_1)) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof Pair))
      return false;
    if (!equals(this.bd_1, other.bd_1))
      return false;
    if (!equals(this.cd_1, other.cd_1))
      return false;
    return true;
  }
}
class Companion_12 {
  constructor() {
    Companion_instance_12 = this;
    this.ke_1 = new Uuid(0n, 0n);
    this.le_1 = 16;
    this.me_1 = 128;
  }
  ne(mostSignificantBits, leastSignificantBits) {
    var tmp;
    if (mostSignificantBits === 0n && leastSignificantBits === 0n) {
      tmp = this.ke_1;
    } else {
      tmp = new Uuid(mostSignificantBits, leastSignificantBits);
    }
    return tmp;
  }
  oe(byteArray) {
    // Inline function 'kotlin.require' call
    if (!(byteArray.length === 16)) {
      var message = 'Expected exactly 16 bytes, but was ' + truncateForErrorMessage(byteArray, 32) + ' of size ' + byteArray.length;
      throw IllegalArgumentException.b3(toString_1(message));
    }
    return this.ne(getLongAt(byteArray, 0), getLongAt(byteArray, 8));
  }
  pe() {
    return this.qe();
  }
  qe() {
    return secureRandomUuid();
  }
}
class Uuid {
  constructor(mostSignificantBits, leastSignificantBits) {
    Companion_getInstance_12();
    this.re_1 = mostSignificantBits;
    this.se_1 = leastSignificantBits;
  }
  toString() {
    return this.te();
  }
  te() {
    var bytes = new Int8Array(36);
    formatBytesInto(this.re_1, bytes, 0, 0, 4);
    // Inline function 'kotlin.code' call
    var this_0 = _Char___init__impl__6a9atx(45);
    var tmp$ret$0 = Char__toInt_impl_vasixd(this_0);
    bytes[8] = toByte(tmp$ret$0);
    formatBytesInto(this.re_1, bytes, 9, 4, 6);
    // Inline function 'kotlin.code' call
    var this_1 = _Char___init__impl__6a9atx(45);
    var tmp$ret$1 = Char__toInt_impl_vasixd(this_1);
    bytes[13] = toByte(tmp$ret$1);
    formatBytesInto(this.re_1, bytes, 14, 6, 8);
    // Inline function 'kotlin.code' call
    var this_2 = _Char___init__impl__6a9atx(45);
    var tmp$ret$2 = Char__toInt_impl_vasixd(this_2);
    bytes[18] = toByte(tmp$ret$2);
    formatBytesInto(this.se_1, bytes, 19, 0, 2);
    // Inline function 'kotlin.code' call
    var this_3 = _Char___init__impl__6a9atx(45);
    var tmp$ret$3 = Char__toInt_impl_vasixd(this_3);
    bytes[23] = toByte(tmp$ret$3);
    formatBytesInto(this.se_1, bytes, 24, 2, 8);
    return decodeToString(bytes);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof Uuid))
      return false;
    return this.re_1 === other.re_1 && this.se_1 === other.se_1;
  }
  ue(other) {
    var tmp;
    if (!(this.re_1 === other.re_1)) {
      // Inline function 'kotlin.toULong' call
      var this_0 = this.re_1;
      var tmp0 = _ULong___init__impl__c78o9k(this_0);
      // Inline function 'kotlin.toULong' call
      var this_1 = other.re_1;
      // Inline function 'kotlin.ULong.compareTo' call
      var other_0 = _ULong___init__impl__c78o9k(this_1);
      tmp = ulongCompare(_ULong___get_data__impl__fggpzb(tmp0), _ULong___get_data__impl__fggpzb(other_0));
    } else {
      // Inline function 'kotlin.toULong' call
      var this_2 = this.se_1;
      var tmp0_0 = _ULong___init__impl__c78o9k(this_2);
      // Inline function 'kotlin.toULong' call
      var this_3 = other.se_1;
      // Inline function 'kotlin.ULong.compareTo' call
      var other_1 = _ULong___init__impl__c78o9k(this_3);
      tmp = ulongCompare(_ULong___get_data__impl__fggpzb(tmp0_0), _ULong___get_data__impl__fggpzb(other_1));
    }
    return tmp;
  }
  q1(other) {
    return this.ue(other instanceof Uuid ? other : THROW_CCE());
  }
  hashCode() {
    return getBigIntHashCode(this.re_1 ^ this.se_1);
  }
}
class KSerializer {}
class AbstractPolymorphicSerializer {
  static jf() {
    return createThis(this);
  }
}
class PolymorphicSerializer extends AbstractPolymorphicSerializer {
  constructor(baseClass) {
    return new.target.if(baseClass);
  }
  static if(baseClass) {
    var $this = this.jf();
    $this.ff_1 = baseClass;
    $this.gf_1 = emptyList();
    var tmp = $this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    tmp.hf_1 = lazy(tmp_0, PolymorphicSerializer$descriptor$delegate$lambda($this));
    return $this;
  }
  static kf(baseClass, classAnnotations) {
    var $this = this.if(baseClass);
    $this.gf_1 = asList(classAnnotations);
    return $this;
  }
  ve() {
    var tmp0 = this.hf_1;
    var tmp = KProperty1;
    // Inline function 'kotlin.getValue' call
    getPropertyCallableRef('descriptor', 1, tmp, PolymorphicSerializer$_get_descriptor_$ref_8tw9if(), null);
    return tmp0.d1();
  }
  toString() {
    return 'kotlinx.serialization.PolymorphicSerializer(baseClass: ' + toString_1(this.ff_1) + ')';
  }
}
class SerializationException extends IllegalArgumentException {
  static zf() {
    var $this = this.v9();
    init_kotlinx_serialization_SerializationException($this);
    return $this;
  }
  static xf(message, cause) {
    var $this = this.w9(message, cause);
    init_kotlinx_serialization_SerializationException($this);
    return $this;
  }
}
class MissingFieldException extends SerializationException {
  constructor(message, cause, missingFields, serialName) {
    return new.target.sf(message, cause, missingFields, serialName);
  }
  static sf(message, cause, missingFields, serialName) {
    var $this = this.xf(message, cause);
    captureStack($this, $this.rf_1);
    $this.pf_1 = missingFields;
    $this.qf_1 = serialName;
    return $this;
  }
  static yf(missingFields, serialName) {
    var tmp0_message = missingFields.i() === 1 ? "Field '" + missingFields.w(0) + "' is required for type with serial name '" + serialName + "', but it was missing" : 'Fields ' + toString_1(missingFields) + " are required for type with serial name '" + serialName + "', but they were missing";
    return this.sf(tmp0_message, null, missingFields, serialName);
  }
}
class SerialDescriptor {}
function get_isNullable() {
  return false;
}
class ContextDescriptor {
  constructor(original, kClass) {
    this.bg_1 = original;
    this.cg_1 = kClass;
    this.dg_1 = this.bg_1.eg() + '<' + this.cg_1.qa() + '>';
  }
  eg() {
    return this.dg_1;
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
    return equals(this.bg_1, another.bg_1) && another.cg_1.equals(this.cg_1);
  }
  hashCode() {
    var result = this.cg_1.hashCode();
    result = imul_0(31, result) + getStringHashCode(this.dg_1) | 0;
    return result;
  }
  toString() {
    return 'ContextDescriptor(kClass: ' + toString_1(this.cg_1) + ', original: ' + toString_1(this.bg_1) + ')';
  }
  fg() {
    return this.bg_1.fg();
  }
  ag() {
    return this.bg_1.ag();
  }
  gg() {
    return this.bg_1.gg();
  }
  hg(index) {
    return this.bg_1.hg(index);
  }
  ig(index) {
    return this.bg_1.ig(index);
  }
}
class elementDescriptors$1 {
  constructor($this_elementDescriptors) {
    this.kg_1 = $this_elementDescriptors;
    this.jg_1 = $this_elementDescriptors.gg();
  }
  g() {
    return this.jg_1 > 0;
  }
  h() {
    var tmp = this.kg_1.gg();
    var _unary__edvuaz = this.jg_1;
    this.jg_1 = _unary__edvuaz - 1 | 0;
    return this.kg_1.ig(tmp - _unary__edvuaz | 0);
  }
}
class elementDescriptors$$inlined$Iterable$1 {
  constructor($this_elementDescriptors) {
    this.lg_1 = $this_elementDescriptors;
  }
  f() {
    return new elementDescriptors$1(this.lg_1);
  }
}
class ClassSerialDescriptorBuilder {
  constructor(serialName) {
    this.we_1 = serialName;
    this.xe_1 = false;
    this.ye_1 = emptyList();
    this.ze_1 = ArrayList.y4();
    this.af_1 = HashSet.t6();
    this.bf_1 = ArrayList.y4();
    this.cf_1 = ArrayList.y4();
    this.df_1 = ArrayList.y4();
  }
  mg(elementName, descriptor, annotations, isOptional) {
    // Inline function 'kotlin.require' call
    if (!this.af_1.o(elementName)) {
      var message = "Element with name '" + elementName + "' is already registered in " + this.we_1;
      throw IllegalArgumentException.b3(toString_1(message));
    }
    // Inline function 'kotlin.collections.plusAssign' call
    this.ze_1.o(elementName);
    // Inline function 'kotlin.collections.plusAssign' call
    this.bf_1.o(descriptor);
    // Inline function 'kotlin.collections.plusAssign' call
    this.cf_1.o(annotations);
    // Inline function 'kotlin.collections.plusAssign' call
    this.df_1.o(isOptional);
  }
  ef(elementName, descriptor, annotations, isOptional, $super) {
    annotations = annotations === VOID ? emptyList() : annotations;
    isOptional = isOptional === VOID ? false : isOptional;
    var tmp;
    if ($super === VOID) {
      this.mg(elementName, descriptor, annotations, isOptional);
      tmp = Unit_instance;
    } else {
      tmp = $super.mg.call(this, elementName, descriptor, annotations, isOptional);
    }
    return tmp;
  }
}
class CachedNames {}
class SerialDescriptorImpl {
  constructor(serialName, kind, elementsCount, typeParameters, builder) {
    this.ng_1 = serialName;
    this.og_1 = kind;
    this.pg_1 = elementsCount;
    this.qg_1 = builder.ye_1;
    this.rg_1 = toHashSet(builder.ze_1);
    var tmp = this;
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_0 = builder.ze_1;
    tmp.sg_1 = copyToArray(this_0);
    this.tg_1 = compactArray(builder.bf_1);
    var tmp_0 = this;
    // Inline function 'kotlin.collections.toTypedArray' call
    var this_1 = builder.cf_1;
    tmp_0.ug_1 = copyToArray(this_1);
    this.vg_1 = toBooleanArray(builder.df_1);
    var tmp_1 = this;
    // Inline function 'kotlin.collections.map' call
    var this_2 = withIndex(this.sg_1);
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList.m(collectionSizeOrDefault(this_2, 10));
    var _iterator__ex2g4s = this_2.f();
    while (_iterator__ex2g4s.g()) {
      var item = _iterator__ex2g4s.h();
      var tmp$ret$4 = to(item.tc_1, item.sc_1);
      destination.o(tmp$ret$4);
    }
    tmp_1.wg_1 = toMap(destination);
    this.xg_1 = compactArray(typeParameters);
    var tmp_2 = this;
    tmp_2.yg_1 = lazy_0(SerialDescriptorImpl$_hashCode$delegate$lambda(this));
  }
  eg() {
    return this.ng_1;
  }
  fg() {
    return this.og_1;
  }
  gg() {
    return this.pg_1;
  }
  zg() {
    return this.rg_1;
  }
  hg(index) {
    return getChecked(this.sg_1, index);
  }
  ig(index) {
    return getChecked(this.tg_1, index);
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
      if (!(this.eg() === other.eg())) {
        tmp$ret$0 = false;
        break $l$block_5;
      }
      if (!contentEquals(this.xg_1, other.xg_1)) {
        tmp$ret$0 = false;
        break $l$block_5;
      }
      if (!(this.gg() === other.gg())) {
        tmp$ret$0 = false;
        break $l$block_5;
      }
      var inductionVariable = 0;
      var last = this.gg();
      if (inductionVariable < last)
        do {
          var index = inductionVariable;
          inductionVariable = inductionVariable + 1 | 0;
          if (!(this.ig(index).eg() === other.ig(index).eg())) {
            tmp$ret$0 = false;
            break $l$block_5;
          }
          if (!equals(this.ig(index).fg(), other.ig(index).fg())) {
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
    return toStringImpl_0(this);
  }
}
class SerialKind {
  toString() {
    return ensureNotNull(getKClassFromExpression(this).qa());
  }
  hashCode() {
    return getStringHashCode(this.toString());
  }
}
class PrimitiveKind extends SerialKind {}
class BOOLEAN extends PrimitiveKind {
  constructor() {
    BOOLEAN_instance = null;
    super();
    BOOLEAN_instance = this;
  }
}
class INT extends PrimitiveKind {
  constructor() {
    INT_instance = null;
    super();
    INT_instance = this;
  }
}
class LONG extends PrimitiveKind {
  constructor() {
    LONG_instance = null;
    super();
    LONG_instance = this;
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
class LIST extends StructureKind {
  constructor() {
    LIST_instance = null;
    super();
    LIST_instance = this;
  }
}
class MAP extends StructureKind {
  constructor() {
    MAP_instance = null;
    super();
    MAP_instance = this;
  }
}
class ListLikeDescriptor {
  constructor(elementDescriptor) {
    this.ch_1 = elementDescriptor;
    this.dh_1 = 1;
  }
  fg() {
    return LIST_getInstance();
  }
  gg() {
    return this.dh_1;
  }
  hg(index) {
    requireNonNegativeIndex(index, this.eg());
    return index.toString();
  }
  ig(index) {
    requireNonNegativeIndex(index, this.eg());
    return this.ch_1;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof ListLikeDescriptor))
      return false;
    if (equals(this.ch_1, other.ch_1) && this.eg() === other.eg())
      return true;
    return false;
  }
  hashCode() {
    return imul_0(hashCode(this.ch_1), 31) + getStringHashCode(this.eg()) | 0;
  }
  toString() {
    return this.eg() + '(' + toString_1(this.ch_1) + ')';
  }
}
class ArrayListClassDesc extends ListLikeDescriptor {
  eg() {
    return 'kotlin.collections.ArrayList';
  }
}
class MapLikeDescriptor {
  constructor(serialName, keyDescriptor, valueDescriptor) {
    this.eh_1 = serialName;
    this.fh_1 = keyDescriptor;
    this.gh_1 = valueDescriptor;
    this.hh_1 = 2;
  }
  eg() {
    return this.eh_1;
  }
  fg() {
    return MAP_getInstance();
  }
  gg() {
    return this.hh_1;
  }
  hg(index) {
    requireNonNegativeIndex(index, this.eg());
    return index.toString();
  }
  ig(index) {
    requireNonNegativeIndex(index, this.eg());
    var tmp;
    switch (index % 2 | 0) {
      case 0:
        tmp = this.fh_1;
        break;
      case 1:
        tmp = this.gh_1;
        break;
      default:
        // Inline function 'kotlin.error' call

        var message = 'Unreached';
        throw IllegalStateException.p2(toString_1(message));
    }
    return tmp;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof MapLikeDescriptor))
      return false;
    if (!(this.eg() === other.eg()))
      return false;
    if (!equals(this.fh_1, other.fh_1))
      return false;
    if (!equals(this.gh_1, other.gh_1))
      return false;
    return true;
  }
  hashCode() {
    var result = getStringHashCode(this.eg());
    result = imul_0(31, result) + hashCode(this.fh_1) | 0;
    result = imul_0(31, result) + hashCode(this.gh_1) | 0;
    return result;
  }
  toString() {
    return this.eg() + '(' + toString_1(this.fh_1) + ', ' + toString_1(this.gh_1) + ')';
  }
}
class LinkedHashMapClassDesc extends MapLikeDescriptor {
  constructor(keyDesc, valueDesc) {
    super('kotlin.collections.LinkedHashMap', keyDesc, valueDesc);
  }
}
class AbstractCollectionSerializer {}
class CollectionLikeSerializer extends AbstractCollectionSerializer {
  constructor(elementSerializer) {
    super();
    this.nh_1 = elementSerializer;
  }
}
class CollectionSerializer extends CollectionLikeSerializer {}
class ArrayListSerializer extends CollectionSerializer {
  constructor(element) {
    super(element);
    this.jh_1 = new ArrayListClassDesc(element.ve());
  }
  ve() {
    return this.jh_1;
  }
}
class MapLikeSerializer extends AbstractCollectionSerializer {
  constructor(keySerializer, valueSerializer) {
    super();
    this.oh_1 = keySerializer;
    this.ph_1 = valueSerializer;
  }
}
class LinkedHashMapSerializer extends MapLikeSerializer {
  constructor(kSerializer, vSerializer) {
    super(kSerializer, vSerializer);
    this.mh_1 = new LinkedHashMapClassDesc(kSerializer.ve(), vSerializer.ve());
  }
  ve() {
    return this.mh_1;
  }
}
class NullableSerializer {
  constructor(serializer) {
    this.qh_1 = serializer;
    this.rh_1 = new SerialDescriptorForNullable(this.qh_1.ve());
  }
  ve() {
    return this.rh_1;
  }
  equals(other) {
    if (this === other)
      return true;
    if (other == null || !getKClassFromExpression(this).equals(getKClassFromExpression(other)))
      return false;
    if (!(other instanceof NullableSerializer))
      THROW_CCE();
    if (!equals(this.qh_1, other.qh_1))
      return false;
    return true;
  }
  hashCode() {
    return hashCode(this.qh_1);
  }
}
class SerialDescriptorForNullable {
  constructor(original) {
    this.sh_1 = original;
    this.th_1 = this.sh_1.eg() + '?';
    this.uh_1 = cachedSerialNames(this.sh_1);
  }
  eg() {
    return this.th_1;
  }
  zg() {
    return this.uh_1;
  }
  ag() {
    return true;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof SerialDescriptorForNullable))
      return false;
    if (!equals(this.sh_1, other.sh_1))
      return false;
    return true;
  }
  toString() {
    return toString_1(this.sh_1) + '?';
  }
  hashCode() {
    return imul_0(hashCode(this.sh_1), 31);
  }
  fg() {
    return this.sh_1.fg();
  }
  gg() {
    return this.sh_1.gg();
  }
  hg(index) {
    return this.sh_1.hg(index);
  }
  ig(index) {
    return this.sh_1.ig(index);
  }
}
class PluginGeneratedSerialDescriptor {
  constructor(serialName, generatedSerializer, elementsCount) {
    generatedSerializer = generatedSerializer === VOID ? null : generatedSerializer;
    this.vh_1 = serialName;
    this.wh_1 = generatedSerializer;
    this.xh_1 = elementsCount;
    this.yh_1 = -1;
    var tmp = this;
    var tmp_0 = 0;
    var tmp_1 = this.xh_1;
    // Inline function 'kotlin.arrayOfNulls' call
    var tmp_2 = Array(tmp_1);
    while (tmp_0 < tmp_1) {
      tmp_2[tmp_0] = '[UNINITIALIZED]';
      tmp_0 = tmp_0 + 1 | 0;
    }
    tmp.zh_1 = tmp_2;
    var tmp_3 = this;
    // Inline function 'kotlin.arrayOfNulls' call
    var size = this.xh_1;
    tmp_3.ai_1 = Array(size);
    this.bi_1 = null;
    this.ci_1 = booleanArray(this.xh_1);
    this.di_1 = emptyMap();
    var tmp_4 = this;
    var tmp_5 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    tmp_4.ei_1 = lazy(tmp_5, PluginGeneratedSerialDescriptor$childSerializers$delegate$lambda(this));
    var tmp_6 = this;
    var tmp_7 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    tmp_6.fi_1 = lazy(tmp_7, PluginGeneratedSerialDescriptor$typeParameterDescriptors$delegate$lambda(this));
    var tmp_8 = this;
    var tmp_9 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    tmp_8.gi_1 = lazy(tmp_9, PluginGeneratedSerialDescriptor$_hashCode$delegate$lambda(this));
  }
  eg() {
    return this.vh_1;
  }
  gg() {
    return this.xh_1;
  }
  fg() {
    return CLASS_getInstance();
  }
  zg() {
    return this.di_1.i1();
  }
  ji() {
    var tmp0 = this.fi_1;
    var tmp = KProperty1;
    // Inline function 'kotlin.getValue' call
    getPropertyCallableRef('typeParameterDescriptors', 1, tmp, PluginGeneratedSerialDescriptor$_get_typeParameterDescriptors_$ref_jk3pka(), null);
    return tmp0.d1();
  }
  ki(name, isOptional) {
    this.yh_1 = this.yh_1 + 1 | 0;
    this.zh_1[this.yh_1] = name;
    this.ci_1[this.yh_1] = isOptional;
    this.ai_1[this.yh_1] = null;
    if (this.yh_1 === (this.xh_1 - 1 | 0)) {
      this.di_1 = buildIndices(this);
    }
  }
  ig(index) {
    return getChecked(_get_childSerializers__7vnyfa(this), index).ve();
  }
  hg(index) {
    return getChecked(this.zh_1, index);
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
      if (!(this.eg() === other.eg())) {
        tmp$ret$0 = false;
        break $l$block_5;
      }
      if (!contentEquals(this.ji(), other.ji())) {
        tmp$ret$0 = false;
        break $l$block_5;
      }
      if (!(this.gg() === other.gg())) {
        tmp$ret$0 = false;
        break $l$block_5;
      }
      var inductionVariable = 0;
      var last = this.gg();
      if (inductionVariable < last)
        do {
          var index = inductionVariable;
          inductionVariable = inductionVariable + 1 | 0;
          if (!(this.ig(index).eg() === other.ig(index).eg())) {
            tmp$ret$0 = false;
            break $l$block_5;
          }
          if (!equals(this.ig(index).fg(), other.ig(index).fg())) {
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
    return toStringImpl_0(this);
  }
}
class GeneratedSerializer {}
function typeParametersSerializers() {
  return get_EMPTY_SERIALIZER_ARRAY();
}
class StringSerializer {
  constructor() {
    StringSerializer_instance = this;
    this.li_1 = new PrimitiveSerialDescriptor('kotlin.String', STRING_getInstance());
  }
  ve() {
    return this.li_1;
  }
}
class IntSerializer {
  constructor() {
    IntSerializer_instance = this;
    this.mi_1 = new PrimitiveSerialDescriptor('kotlin.Int', INT_getInstance());
  }
  ve() {
    return this.mi_1;
  }
}
class PrimitiveSerialDescriptor {
  constructor(serialName, kind) {
    this.ni_1 = serialName;
    this.oi_1 = kind;
  }
  eg() {
    return this.ni_1;
  }
  fg() {
    return this.oi_1;
  }
  gg() {
    return 0;
  }
  hg(index) {
    error(this);
  }
  ig(index) {
    error(this);
  }
  toString() {
    return 'PrimitiveDescriptor(' + this.ni_1 + ')';
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof PrimitiveSerialDescriptor))
      return false;
    if (this.ni_1 === other.ni_1 && equals(this.oi_1, other.oi_1))
      return true;
    return false;
  }
  hashCode() {
    return getStringHashCode(this.ni_1) + imul_0(31, this.oi_1.hashCode()) | 0;
  }
}
class LongSerializer {
  constructor() {
    LongSerializer_instance = this;
    this.pi_1 = new PrimitiveSerialDescriptor('kotlin.Long', LONG_getInstance());
  }
  ve() {
    return this.pi_1;
  }
}
class BooleanSerializer {
  constructor() {
    BooleanSerializer_instance = this;
    this.qi_1 = new PrimitiveSerialDescriptor('kotlin.Boolean', BOOLEAN_getInstance());
  }
  ve() {
    return this.qi_1;
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
class Companion_13 {
  constructor() {
    Companion_instance_13 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.dsl.cqrs.envelope.Envelope', null, 7);
    tmp0_serialDesc.ki('id', false);
    tmp0_serialDesc.ki('data', false);
    tmp0_serialDesc.ki('type', false);
    tmp0_serialDesc.ki('datacontenttype', true);
    tmp0_serialDesc.ki('specversion', true);
    tmp0_serialDesc.ki('source', true);
    tmp0_serialDesc.ki('time', true);
    this.yi_1 = tmp0_serialDesc;
  }
}
class Envelope {
  constructor(id, data, type, datacontenttype, specversion, source, time) {
    Companion_getInstance_13();
    datacontenttype = datacontenttype === VOID ? null : datacontenttype;
    specversion = specversion === VOID ? null : specversion;
    source = source === VOID ? null : source;
    time = time === VOID ? null : time;
    this.zi_1 = id;
    this.aj_1 = data;
    this.bj_1 = type;
    this.cj_1 = datacontenttype;
    this.dj_1 = specversion;
    this.ej_1 = source;
    this.fj_1 = time;
  }
  ri() {
    return this.zi_1;
  }
  si() {
    return this.aj_1;
  }
  ti() {
    return this.bj_1;
  }
  ui() {
    return this.cj_1;
  }
  vi() {
    return this.dj_1;
  }
  wi() {
    return this.ej_1;
  }
  xi() {
    return this.fj_1;
  }
  static gj(seen0, id, data, type, datacontenttype, specversion, source, time, serializationConstructorMarker) {
    Companion_getInstance_13();
    if (!(7 === (7 & seen0))) {
      throwMissingFieldException(seen0, 7, Companion_getInstance_13().yi_1);
    }
    var $this = createThis(this);
    $this.zi_1 = id;
    $this.aj_1 = data;
    $this.bj_1 = type;
    if (0 === (seen0 & 8))
      $this.cj_1 = null;
    else
      $this.cj_1 = datacontenttype;
    if (0 === (seen0 & 16))
      $this.dj_1 = null;
    else
      $this.dj_1 = specversion;
    if (0 === (seen0 & 32))
      $this.ej_1 = null;
    else
      $this.ej_1 = source;
    if (0 === (seen0 & 64))
      $this.fj_1 = null;
    else
      $this.fj_1 = time;
    return $this;
  }
  get id() {
    return this.ri();
  }
  get data() {
    return this.si();
  }
  get type() {
    return this.ti();
  }
  get datacontenttype() {
    return this.ui();
  }
  get specversion() {
    return this.vi();
  }
  get source() {
    return this.wi();
  }
  get time() {
    return this.xi();
  }
}
class F2ErrorDTO {}
class Companion_14 {}
class $serializer {
  constructor() {
    $serializer_instance = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.dsl.cqrs.error.F2Error', this, 5);
    tmp0_serialDesc.ki('message', false);
    tmp0_serialDesc.ki('id', true);
    tmp0_serialDesc.ki('timestamp', true);
    tmp0_serialDesc.ki('code', true);
    tmp0_serialDesc.ki('requestId', true);
    this.kj_1 = tmp0_serialDesc;
  }
  ve() {
    return this.kj_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), get_nullable(StringSerializer_getInstance()), StringSerializer_getInstance(), IntSerializer_getInstance(), get_nullable(StringSerializer_getInstance())];
  }
}
class F2Error {
  constructor(message, id, timestamp, code, requestId) {
    id = id === VOID ? Companion_getInstance_12().pe().toString() : id;
    timestamp = timestamp === VOID ? System_instance.td().toString() : timestamp;
    code = code === VOID ? 500 : code;
    requestId = requestId === VOID ? null : requestId;
    this.lj_1 = message;
    this.mj_1 = id;
    this.nj_1 = timestamp;
    this.oj_1 = code;
    this.pj_1 = requestId;
  }
  t9() {
    return this.lj_1;
  }
  ri() {
    return this.mj_1;
  }
  hj() {
    return this.nj_1;
  }
  ij() {
    return this.oj_1;
  }
  jj() {
    return this.pj_1;
  }
  toString() {
    return "F2Error(timestamp='" + this.timestamp + "', code=" + this.code + ", requestId='" + this.requestId + "', message='" + this.message + "')";
  }
  static qj(seen0, message, id, timestamp, code, requestId, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance().kj_1);
    }
    var $this = createThis(this);
    $this.lj_1 = message;
    if (0 === (seen0 & 2))
      $this.mj_1 = Companion_getInstance_12().pe().toString();
    else
      $this.mj_1 = id;
    if (0 === (seen0 & 4))
      $this.nj_1 = System_instance.td().toString();
    else
      $this.nj_1 = timestamp;
    if (0 === (seen0 & 8))
      $this.oj_1 = 500;
    else
      $this.oj_1 = code;
    if (0 === (seen0 & 16))
      $this.pj_1 = null;
    else
      $this.pj_1 = requestId;
    return $this;
  }
  get message() {
    return this.t9();
  }
  get id() {
    return this.ri();
  }
  get timestamp() {
    return this.hj();
  }
  get code() {
    return this.ij();
  }
  get requestId() {
    return this.jj();
  }
}
class Companion_15 {
  rj(message, id, requestId, code, cause) {
    var tmp0_timestamp = System_instance.td().toString();
    return F2Exception.vj(new F2Error(message, id, tmp0_timestamp, code, requestId), cause);
  }
  invoke(message, id, requestId, code, cause, $super) {
    id = id === VOID ? '' : id;
    requestId = requestId === VOID ? '' : requestId;
    code = code === VOID ? 500 : code;
    cause = cause === VOID ? null : cause;
    return $super === VOID ? this.rj(message, id, requestId, code, cause) : $super.rj.call(this, message, id, requestId, code, cause);
  }
}
class F2Exception extends RuntimeException {
  constructor(error, cause) {
    return new.target.vj(error, cause);
  }
  static vj(error, cause) {
    cause = cause === VOID ? null : cause;
    var $this = this.x9(error.message, cause);
    captureStack($this, $this.uj_1);
    $this.error = error;
    return $this;
  }
  wj() {
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
    this.yj_1 = matches;
    this.zj_1 = negative;
  }
  xj() {
    return this.zj_1;
  }
  map(transform) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = this.yj_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList.m(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.f();
    while (_iterator__ex2g4s.g()) {
      var item = _iterator__ex2g4s.h();
      var tmp$ret$2 = item.map(transform);
      destination.o(tmp$ret$2);
    }
    return new AndMatch(destination, this.zj_1);
  }
  not() {
    return this.ak(VOID, !this.zj_1);
  }
  and(match) {
    return this.ak(plus(this.yj_1, match));
  }
  bk(matches, negative) {
    return new AndMatch(matches, negative);
  }
  ak(matches, negative, $super) {
    matches = matches === VOID ? this.yj_1 : matches;
    negative = negative === VOID ? this.zj_1 : negative;
    return $super === VOID ? this.bk(matches, negative) : $super.bk.call(this, matches, negative);
  }
  toString() {
    return 'AndMatch(matches=' + toString_1(this.yj_1) + ', negative=' + this.zj_1 + ')';
  }
  hashCode() {
    var result = hashCode(this.yj_1);
    result = imul_0(result, 31) + getBooleanHashCode(this.zj_1) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof AndMatch))
      return false;
    if (!equals(this.yj_1, other.yj_1))
      return false;
    if (!(this.zj_1 === other.zj_1))
      return false;
    return true;
  }
  get negative() {
    return this.xj();
  }
}
class OrMatch {
  constructor(matches, negative) {
    negative = negative === VOID ? false : negative;
    this.ck_1 = matches;
    this.dk_1 = negative;
  }
  xj() {
    return this.dk_1;
  }
  map(transform) {
    // Inline function 'kotlin.collections.map' call
    var this_0 = this.ck_1;
    // Inline function 'kotlin.collections.mapTo' call
    var destination = ArrayList.m(collectionSizeOrDefault(this_0, 10));
    var _iterator__ex2g4s = this_0.f();
    while (_iterator__ex2g4s.g()) {
      var item = _iterator__ex2g4s.h();
      var tmp$ret$2 = item.map(transform);
      destination.o(tmp$ret$2);
    }
    return new OrMatch(destination, this.dk_1);
  }
  not() {
    return this.ek(VOID, !this.dk_1);
  }
  or(match) {
    return this.ek(plus(this.ck_1, match));
  }
  bk(matches, negative) {
    return new OrMatch(matches, negative);
  }
  ek(matches, negative, $super) {
    matches = matches === VOID ? this.ck_1 : matches;
    negative = negative === VOID ? this.dk_1 : negative;
    return $super === VOID ? this.bk(matches, negative) : $super.bk.call(this, matches, negative);
  }
  toString() {
    return 'OrMatch(matches=' + toString_1(this.ck_1) + ', negative=' + this.dk_1 + ')';
  }
  hashCode() {
    var result = hashCode(this.ck_1);
    result = imul_0(result, 31) + getBooleanHashCode(this.dk_1) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrMatch))
      return false;
    if (!equals(this.ck_1, other.ck_1))
      return false;
    if (!(this.dk_1 === other.dk_1))
      return false;
    return true;
  }
  get negative() {
    return this.xj();
  }
}
class SortDTO {}
class PageDTO {}
class Companion_16 {
  constructor() {
    Companion_instance_16 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.dsl.cqrs.page.Page', null, 2);
    tmp0_serialDesc.ki('total', false);
    tmp0_serialDesc.ki('items', false);
    this.kk_1 = tmp0_serialDesc;
  }
}
class Page {
  constructor(total, items) {
    Companion_getInstance_16();
    this.lk_1 = total;
    this.mk_1 = items;
  }
  ik() {
    return this.lk_1;
  }
  jk() {
    return this.mk_1;
  }
  static nk(seen0, total, items, serializationConstructorMarker) {
    Companion_getInstance_16();
    if (!(3 === (3 & seen0))) {
      throwMissingFieldException(seen0, 3, Companion_getInstance_16().kk_1);
    }
    var $this = createThis(this);
    $this.lk_1 = total;
    $this.mk_1 = items;
    return $this;
  }
  get total() {
    return this.ik();
  }
  get items() {
    return this.jk();
  }
}
class PageQueryDTO {}
class PageQueryResultDTO {}
class Companion_17 {
  constructor() {
    Companion_instance_17 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.pk_1 = [lazy(tmp_0, PageQuery$Companion$$childSerializers$_anonymous__54lki7)];
  }
}
class $serializer_0 {
  constructor() {
    $serializer_instance_0 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.dsl.cqrs.page.PageQuery', this, 1);
    tmp0_serialDesc.ki('pagination', false);
    this.qk_1 = tmp0_serialDesc;
  }
  ve() {
    return this.qk_1;
  }
  hi() {
    var tmp0_cached = Companion_getInstance_17().pk_1;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [get_nullable(tmp0_cached[0].d1())];
  }
}
class PageQuery {
  constructor(pagination) {
    Companion_getInstance_17();
    this.rk_1 = pagination;
  }
  ok() {
    return this.rk_1;
  }
  static sk(seen0, pagination, serializationConstructorMarker) {
    Companion_getInstance_17();
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_0().qk_1);
    }
    var $this = createThis(this);
    $this.rk_1 = pagination;
    return $this;
  }
  get pagination() {
    return this.ok();
  }
}
class Companion_18 {
  constructor() {
    Companion_instance_18 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.dsl.cqrs.page.PageQueryResult', null, 3);
    tmp0_serialDesc.ki('pagination', false);
    tmp0_serialDesc.ki('total', false);
    tmp0_serialDesc.ki('items', false);
    this.tk_1 = tmp0_serialDesc;
  }
}
class PageQueryResult {
  constructor(pagination, total, items) {
    Companion_getInstance_18();
    this.uk_1 = pagination;
    this.vk_1 = total;
    this.wk_1 = items;
  }
  ok() {
    return this.uk_1;
  }
  ik() {
    return this.vk_1;
  }
  jk() {
    return this.wk_1;
  }
  static xk(seen0, pagination, total, items, serializationConstructorMarker) {
    Companion_getInstance_18();
    if (!(7 === (7 & seen0))) {
      throwMissingFieldException(seen0, 7, Companion_getInstance_18().tk_1);
    }
    var $this = createThis(this);
    $this.uk_1 = pagination;
    $this.vk_1 = total;
    $this.wk_1 = items;
    return $this;
  }
  get pagination() {
    return this.ok();
  }
  get total() {
    return this.ik();
  }
  get items() {
    return this.jk();
  }
}
class Companion_19 {}
class Pagination {}
class OffsetPaginationDTO {}
class PagePaginationDTO {}
class Companion_20 {}
class $serializer_1 {
  constructor() {
    $serializer_instance_1 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.dsl.cqrs.page.OffsetPagination', this, 2);
    tmp0_serialDesc.ki('offset', false);
    tmp0_serialDesc.ki('limit', false);
    this.bl_1 = tmp0_serialDesc;
  }
  ve() {
    return this.bl_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [IntSerializer_getInstance(), IntSerializer_getInstance()];
  }
}
class OffsetPagination {
  constructor(offset, limit) {
    this.cl_1 = offset;
    this.dl_1 = limit;
  }
  yk() {
    return this.cl_1;
  }
  zk() {
    return this.dl_1;
  }
  static el(seen0, offset, limit, serializationConstructorMarker) {
    if (!(3 === (3 & seen0))) {
      throwMissingFieldException(seen0, 3, $serializer_getInstance_1().bl_1);
    }
    var $this = createThis(this);
    $this.cl_1 = offset;
    $this.dl_1 = limit;
    return $this;
  }
  get offset() {
    return this.yk();
  }
  get limit() {
    return this.zk();
  }
}
class Companion_21 {}
class $serializer_2 {
  constructor() {
    $serializer_instance_2 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.dsl.cqrs.page.PagePagination', this, 2);
    tmp0_serialDesc.ki('page', false);
    tmp0_serialDesc.ki('size', false);
    this.fl_1 = tmp0_serialDesc;
  }
  ve() {
    return this.fl_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [get_nullable(IntSerializer_getInstance()), get_nullable(IntSerializer_getInstance())];
  }
}
class PagePagination {
  constructor(page, size) {
    this.gl_1 = page;
    this.hl_1 = size;
  }
  al() {
    return this.gl_1;
  }
  i() {
    return this.hl_1;
  }
  static il(seen0, page, size, serializationConstructorMarker) {
    if (!(3 === (3 & seen0))) {
      throwMissingFieldException(seen0, 3, $serializer_getInstance_2().fl_1);
    }
    var $this = createThis(this);
    $this.gl_1 = page;
    $this.hl_1 = size;
    return $this;
  }
  get page() {
    return this.al();
  }
  get size() {
    return this.i();
  }
}
class OrganizationCoreDeleteCommandDTO {}
class Companion_22 {}
class $serializer_3 {
  constructor() {
    $serializer_instance_3 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.core.organization.domain.command.OrganizationCoreDeleteCommand', this, 1);
    tmp0_serialDesc.ki('id', false);
    this.jl_1 = tmp0_serialDesc;
  }
  ve() {
    return this.jl_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance()];
  }
}
class OrganizationCoreDeleteCommand {
  constructor(id) {
    this.kl_1 = id;
  }
  ri() {
    return this.kl_1;
  }
  dd() {
    return this.kl_1;
  }
  ll(id) {
    return new OrganizationCoreDeleteCommand(id);
  }
  copy(id, $super) {
    id = id === VOID ? this.kl_1 : id;
    return $super === VOID ? this.ll(id) : $super.ll.call(this, id);
  }
  toString() {
    return 'OrganizationCoreDeleteCommand(id=' + this.kl_1 + ')';
  }
  hashCode() {
    return getStringHashCode(this.kl_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationCoreDeleteCommand))
      return false;
    if (!(this.kl_1 === other.kl_1))
      return false;
    return true;
  }
  static ml(seen0, id, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_3().jl_1);
    }
    var $this = createThis(this);
    $this.kl_1 = id;
    return $this;
  }
  get id() {
    return this.ri();
  }
}
class OrganizationCoreDeletedEventDTO {}
class Companion_23 {}
class $serializer_4 {
  constructor() {
    $serializer_instance_4 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.core.organization.domain.command.OrganizationCoreDeletedEvent', this, 1);
    tmp0_serialDesc.ki('id', false);
    this.nl_1 = tmp0_serialDesc;
  }
  ve() {
    return this.nl_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance()];
  }
}
class OrganizationCoreDeletedEvent {
  constructor(id) {
    this.ol_1 = id;
  }
  ri() {
    return this.ol_1;
  }
  dd() {
    return this.ol_1;
  }
  ll(id) {
    return new OrganizationCoreDeletedEvent(id);
  }
  copy(id, $super) {
    id = id === VOID ? this.ol_1 : id;
    return $super === VOID ? this.ll(id) : $super.ll.call(this, id);
  }
  toString() {
    return 'OrganizationCoreDeletedEvent(id=' + this.ol_1 + ')';
  }
  hashCode() {
    return getStringHashCode(this.ol_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationCoreDeletedEvent))
      return false;
    if (!(this.ol_1 === other.ol_1))
      return false;
    return true;
  }
  static pl(seen0, id, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_4().nl_1);
    }
    var $this = createThis(this);
    $this.ol_1 = id;
    return $this;
  }
  get id() {
    return this.ri();
  }
}
class AuthedUserDTO {}
class ImPermission extends Enum {
  constructor(name, ordinal, identifier) {
    super(name, ordinal);
    this.identifier = identifier;
  }
  ql() {
    return this.identifier;
  }
  get name() {
    return this.n1();
  }
  get ordinal() {
    return this.o1();
  }
}
class ExceptionCodes {
  privilegeWrongTarget() {
    return 1000;
  }
}
class AddressDTO {}
class Companion_24 {}
class $serializer_5 {
  constructor() {
    $serializer_instance_5 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.commons.model.Address', this, 3);
    tmp0_serialDesc.ki('street', false);
    tmp0_serialDesc.ki('postalCode', false);
    tmp0_serialDesc.ki('city', false);
    this.zl_1 = tmp0_serialDesc;
  }
  ve() {
    return this.zl_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), StringSerializer_getInstance(), StringSerializer_getInstance()];
  }
}
class Address {
  constructor(street, postalCode, city) {
    this.am_1 = street;
    this.bm_1 = postalCode;
    this.cm_1 = city;
  }
  wl() {
    return this.am_1;
  }
  xl() {
    return this.bm_1;
  }
  yl() {
    return this.cm_1;
  }
  dd() {
    return this.am_1;
  }
  ed() {
    return this.bm_1;
  }
  dm() {
    return this.cm_1;
  }
  em(street, postalCode, city) {
    return new Address(street, postalCode, city);
  }
  copy(street, postalCode, city, $super) {
    street = street === VOID ? this.am_1 : street;
    postalCode = postalCode === VOID ? this.bm_1 : postalCode;
    city = city === VOID ? this.cm_1 : city;
    return $super === VOID ? this.em(street, postalCode, city) : $super.em.call(this, street, postalCode, city);
  }
  toString() {
    return 'Address(street=' + this.am_1 + ', postalCode=' + this.bm_1 + ', city=' + this.cm_1 + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.am_1);
    result = imul_0(result, 31) + getStringHashCode(this.bm_1) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.cm_1) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof Address))
      return false;
    if (!(this.am_1 === other.am_1))
      return false;
    if (!(this.bm_1 === other.bm_1))
      return false;
    if (!(this.cm_1 === other.cm_1))
      return false;
    return true;
  }
  static fm(seen0, street, postalCode, city, serializationConstructorMarker) {
    if (!(7 === (7 & seen0))) {
      throwMissingFieldException(seen0, 7, $serializer_getInstance_5().zl_1);
    }
    var $this = createThis(this);
    $this.am_1 = street;
    $this.bm_1 = postalCode;
    $this.cm_1 = city;
    return $this;
  }
  get street() {
    return this.wl();
  }
  get postalCode() {
    return this.xl();
  }
  get city() {
    return this.yl();
  }
}
class PrivilegePolicies {
  canGet(authedUser) {
    return hasRole(authedUser, ImPermission_IM_ROLE_READ_getInstance());
  }
  canList(authedUser) {
    return hasRole(authedUser, ImPermission_IM_ROLE_READ_getInstance());
  }
  canDefine(authedUser) {
    return hasRole(authedUser, ImPermission_IM_ROLE_WRITE_getInstance());
  }
}
class FeatureDefineCommandDTO {}
class Companion_25 {}
class $serializer_6 {
  constructor() {
    $serializer_instance_6 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.feature.command.FeatureDefineCommand', this, 2);
    tmp0_serialDesc.ki('identifier', false);
    tmp0_serialDesc.ki('description', false);
    this.hm_1 = tmp0_serialDesc;
  }
  ve() {
    return this.hm_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), StringSerializer_getInstance()];
  }
}
class FeatureDefineCommand {
  constructor(identifier, description) {
    this.im_1 = identifier;
    this.jm_1 = description;
  }
  ql() {
    return this.im_1;
  }
  gm() {
    return this.jm_1;
  }
  dd() {
    return this.im_1;
  }
  ed() {
    return this.jm_1;
  }
  km(identifier, description) {
    return new FeatureDefineCommand(identifier, description);
  }
  copy(identifier, description, $super) {
    identifier = identifier === VOID ? this.im_1 : identifier;
    description = description === VOID ? this.jm_1 : description;
    return $super === VOID ? this.km(identifier, description) : $super.km.call(this, identifier, description);
  }
  toString() {
    return 'FeatureDefineCommand(identifier=' + this.im_1 + ', description=' + this.jm_1 + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.im_1);
    result = imul_0(result, 31) + getStringHashCode(this.jm_1) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof FeatureDefineCommand))
      return false;
    if (!(this.im_1 === other.im_1))
      return false;
    if (!(this.jm_1 === other.jm_1))
      return false;
    return true;
  }
  static lm(seen0, identifier, description, serializationConstructorMarker) {
    if (!(3 === (3 & seen0))) {
      throwMissingFieldException(seen0, 3, $serializer_getInstance_6().hm_1);
    }
    var $this = createThis(this);
    $this.im_1 = identifier;
    $this.jm_1 = description;
    return $this;
  }
  get identifier() {
    return this.ql();
  }
  get description() {
    return this.gm();
  }
}
class FeatureDefinedEventDTO {}
class Companion_26 {}
class $serializer_7 {
  constructor() {
    $serializer_instance_7 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.feature.command.FeatureDefinedEvent', this, 1);
    tmp0_serialDesc.ki('identifier', false);
    this.mm_1 = tmp0_serialDesc;
  }
  ve() {
    return this.mm_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance()];
  }
}
class FeatureDefinedEvent {
  constructor(identifier) {
    this.nm_1 = identifier;
  }
  ql() {
    return this.nm_1;
  }
  dd() {
    return this.nm_1;
  }
  ll(identifier) {
    return new FeatureDefinedEvent(identifier);
  }
  copy(identifier, $super) {
    identifier = identifier === VOID ? this.nm_1 : identifier;
    return $super === VOID ? this.ll(identifier) : $super.ll.call(this, identifier);
  }
  toString() {
    return 'FeatureDefinedEvent(identifier=' + this.nm_1 + ')';
  }
  hashCode() {
    return getStringHashCode(this.nm_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof FeatureDefinedEvent))
      return false;
    if (!(this.nm_1 === other.nm_1))
      return false;
    return true;
  }
  static om(seen0, identifier, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_7().mm_1);
    }
    var $this = createThis(this);
    $this.nm_1 = identifier;
    return $this;
  }
  get identifier() {
    return this.ql();
  }
}
class PrivilegeDTO {}
class FeatureDTO {}
class Companion_27 {}
class $serializer_8 {
  constructor() {
    $serializer_instance_8 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.feature.model.Feature', this, 4);
    tmp0_serialDesc.ki('id', false);
    tmp0_serialDesc.ki('identifier', false);
    tmp0_serialDesc.ki('description', false);
    tmp0_serialDesc.ki('type', true);
    this.pm_1 = tmp0_serialDesc;
  }
  ve() {
    return this.pm_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), StringSerializer_getInstance(), StringSerializer_getInstance(), StringSerializer_getInstance()];
  }
}
class Feature {
  constructor(id, identifier, description, type) {
    type = type === VOID ? 'FEATURE' : type;
    this.qm_1 = id;
    this.rm_1 = identifier;
    this.sm_1 = description;
    this.tm_1 = type;
  }
  ri() {
    return this.qm_1;
  }
  ql() {
    return this.rm_1;
  }
  gm() {
    return this.sm_1;
  }
  ti() {
    return this.tm_1;
  }
  dd() {
    return this.qm_1;
  }
  ed() {
    return this.rm_1;
  }
  dm() {
    return this.sm_1;
  }
  um() {
    return this.tm_1;
  }
  vm(id, identifier, description, type) {
    return new Feature(id, identifier, description, type);
  }
  copy(id, identifier, description, type, $super) {
    id = id === VOID ? this.qm_1 : id;
    identifier = identifier === VOID ? this.rm_1 : identifier;
    description = description === VOID ? this.sm_1 : description;
    type = type === VOID ? this.tm_1 : type;
    return $super === VOID ? this.vm(id, identifier, description, type) : $super.vm.call(this, id, identifier, description, type);
  }
  toString() {
    return 'Feature(id=' + this.qm_1 + ', identifier=' + this.rm_1 + ', description=' + this.sm_1 + ', type=' + this.tm_1 + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.qm_1);
    result = imul_0(result, 31) + getStringHashCode(this.rm_1) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.sm_1) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.tm_1) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof Feature))
      return false;
    if (!(this.qm_1 === other.qm_1))
      return false;
    if (!(this.rm_1 === other.rm_1))
      return false;
    if (!(this.sm_1 === other.sm_1))
      return false;
    if (!(this.tm_1 === other.tm_1))
      return false;
    return true;
  }
  static wm(seen0, id, identifier, description, type, serializationConstructorMarker) {
    if (!(7 === (7 & seen0))) {
      throwMissingFieldException(seen0, 7, $serializer_getInstance_8().pm_1);
    }
    var $this = createThis(this);
    $this.qm_1 = id;
    $this.rm_1 = identifier;
    $this.sm_1 = description;
    if (0 === (seen0 & 8))
      $this.tm_1 = 'FEATURE';
    else
      $this.tm_1 = type;
    return $this;
  }
  get id() {
    return this.ri();
  }
  get identifier() {
    return this.ql();
  }
  get description() {
    return this.gm();
  }
  get type() {
    return this.ti();
  }
}
class FeatureGetQueryDTO {}
class Companion_28 {}
class $serializer_9 {
  constructor() {
    $serializer_instance_9 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.feature.query.FeatureGetQuery', this, 1);
    tmp0_serialDesc.ki('identifier', false);
    this.xm_1 = tmp0_serialDesc;
  }
  ve() {
    return this.xm_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance()];
  }
}
class FeatureGetQuery {
  constructor(identifier) {
    this.ym_1 = identifier;
  }
  ql() {
    return this.ym_1;
  }
  dd() {
    return this.ym_1;
  }
  ll(identifier) {
    return new FeatureGetQuery(identifier);
  }
  copy(identifier, $super) {
    identifier = identifier === VOID ? this.ym_1 : identifier;
    return $super === VOID ? this.ll(identifier) : $super.ll.call(this, identifier);
  }
  toString() {
    return 'FeatureGetQuery(identifier=' + this.ym_1 + ')';
  }
  hashCode() {
    return getStringHashCode(this.ym_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof FeatureGetQuery))
      return false;
    if (!(this.ym_1 === other.ym_1))
      return false;
    return true;
  }
  static zm(seen0, identifier, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_9().xm_1);
    }
    var $this = createThis(this);
    $this.ym_1 = identifier;
    return $this;
  }
  get identifier() {
    return this.ql();
  }
}
class FeatureGetResultDTO {}
class Companion_29 {}
class $serializer_10 {
  constructor() {
    $serializer_instance_10 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.feature.query.FeatureGetResult', this, 1);
    tmp0_serialDesc.ki('item', false);
    this.bn_1 = tmp0_serialDesc;
  }
  ve() {
    return this.bn_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [get_nullable($serializer_getInstance_8())];
  }
}
class FeatureGetResult {
  constructor(item) {
    this.cn_1 = item;
  }
  an() {
    return this.cn_1;
  }
  dd() {
    return this.cn_1;
  }
  dn(item) {
    return new FeatureGetResult(item);
  }
  copy(item, $super) {
    item = item === VOID ? this.cn_1 : item;
    return $super === VOID ? this.dn(item) : $super.dn.call(this, item);
  }
  toString() {
    return 'FeatureGetResult(item=' + toString_0(this.cn_1) + ')';
  }
  hashCode() {
    return this.cn_1 == null ? 0 : this.cn_1.hashCode();
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof FeatureGetResult))
      return false;
    if (!equals(this.cn_1, other.cn_1))
      return false;
    return true;
  }
  static en(seen0, item, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_10().bn_1);
    }
    var $this = createThis(this);
    $this.cn_1 = item;
    return $this;
  }
  get item() {
    return this.an();
  }
}
class FeatureListQueryDTO {}
class Companion_30 {}
class $serializer_11 {
  constructor() {
    $serializer_instance_11 = this;
    this.fn_1 = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.feature.query.FeatureListQuery', this, 0);
  }
  ve() {
    return this.fn_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [];
  }
}
class FeatureListQuery {
  static gn(seen0, serializationConstructorMarker) {
    if (!(0 === (0 & seen0))) {
      throwMissingFieldException(seen0, 0, $serializer_getInstance_11().fn_1);
    }
    return createThis(this);
  }
}
class FeatureListResultDTO {}
class Companion_31 {
  constructor() {
    Companion_instance_31 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.hn_1 = [lazy(tmp_0, FeatureListResult$Companion$$childSerializers$_anonymous__ad24xl)];
  }
}
class $serializer_12 {
  constructor() {
    $serializer_instance_12 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.feature.query.FeatureListResult', this, 1);
    tmp0_serialDesc.ki('items', false);
    this.in_1 = tmp0_serialDesc;
  }
  ve() {
    return this.in_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [Companion_getInstance_31().hn_1[0].d1()];
  }
}
class FeatureListResult {
  constructor(items) {
    Companion_getInstance_31();
    this.jn_1 = items;
  }
  jk() {
    return this.jn_1;
  }
  dd() {
    return this.jn_1;
  }
  kn(items) {
    return new FeatureListResult(items);
  }
  copy(items, $super) {
    items = items === VOID ? this.jn_1 : items;
    return $super === VOID ? this.kn(items) : $super.kn.call(this, items);
  }
  toString() {
    return 'FeatureListResult(items=' + toString_1(this.jn_1) + ')';
  }
  hashCode() {
    return hashCode(this.jn_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof FeatureListResult))
      return false;
    if (!equals(this.jn_1, other.jn_1))
      return false;
    return true;
  }
  static ln(seen0, items, serializationConstructorMarker) {
    Companion_getInstance_31();
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_12().in_1);
    }
    var $this = createThis(this);
    $this.jn_1 = items;
    return $this;
  }
  get items() {
    return this.jk();
  }
}
class PermissionDefineCommandDTO {}
class Companion_32 {
  constructor() {
    Companion_instance_32 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.nn_1 = [null, null, lazy(tmp_0, PermissionDefineCommand$Companion$$childSerializers$_anonymous__iqcvl5)];
  }
}
class $serializer_13 {
  constructor() {
    $serializer_instance_13 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.permission.command.PermissionDefineCommand', this, 3);
    tmp0_serialDesc.ki('identifier', false);
    tmp0_serialDesc.ki('description', false);
    tmp0_serialDesc.ki('features', false);
    this.on_1 = tmp0_serialDesc;
  }
  ve() {
    return this.on_1;
  }
  hi() {
    var tmp0_cached = Companion_getInstance_32().nn_1;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), StringSerializer_getInstance(), get_nullable(tmp0_cached[2].d1())];
  }
}
class PermissionDefineCommand {
  constructor(identifier, description, features) {
    Companion_getInstance_32();
    this.pn_1 = identifier;
    this.qn_1 = description;
    this.rn_1 = features;
  }
  ql() {
    return this.pn_1;
  }
  gm() {
    return this.qn_1;
  }
  mn() {
    return this.rn_1;
  }
  dd() {
    return this.pn_1;
  }
  ed() {
    return this.qn_1;
  }
  dm() {
    return this.rn_1;
  }
  sn(identifier, description, features) {
    return new PermissionDefineCommand(identifier, description, features);
  }
  copy(identifier, description, features, $super) {
    identifier = identifier === VOID ? this.pn_1 : identifier;
    description = description === VOID ? this.qn_1 : description;
    features = features === VOID ? this.rn_1 : features;
    return $super === VOID ? this.sn(identifier, description, features) : $super.sn.call(this, identifier, description, features);
  }
  toString() {
    return 'PermissionDefineCommand(identifier=' + this.pn_1 + ', description=' + this.qn_1 + ', features=' + toString_0(this.rn_1) + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.pn_1);
    result = imul_0(result, 31) + getStringHashCode(this.qn_1) | 0;
    result = imul_0(result, 31) + (this.rn_1 == null ? 0 : hashCode(this.rn_1)) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof PermissionDefineCommand))
      return false;
    if (!(this.pn_1 === other.pn_1))
      return false;
    if (!(this.qn_1 === other.qn_1))
      return false;
    if (!equals(this.rn_1, other.rn_1))
      return false;
    return true;
  }
  static tn(seen0, identifier, description, features, serializationConstructorMarker) {
    Companion_getInstance_32();
    if (!(7 === (7 & seen0))) {
      throwMissingFieldException(seen0, 7, $serializer_getInstance_13().on_1);
    }
    var $this = createThis(this);
    $this.pn_1 = identifier;
    $this.qn_1 = description;
    $this.rn_1 = features;
    return $this;
  }
  get identifier() {
    return this.ql();
  }
  get description() {
    return this.gm();
  }
  get features() {
    return this.mn();
  }
}
class PermissionDefinedEventDTO {}
class Companion_33 {}
class $serializer_14 {
  constructor() {
    $serializer_instance_14 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.permission.command.PermissionDefinedEvent', this, 1);
    tmp0_serialDesc.ki('identifier', false);
    this.un_1 = tmp0_serialDesc;
  }
  ve() {
    return this.un_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance()];
  }
}
class PermissionDefinedEvent {
  constructor(identifier) {
    this.vn_1 = identifier;
  }
  ql() {
    return this.vn_1;
  }
  dd() {
    return this.vn_1;
  }
  ll(identifier) {
    return new PermissionDefinedEvent(identifier);
  }
  copy(identifier, $super) {
    identifier = identifier === VOID ? this.vn_1 : identifier;
    return $super === VOID ? this.ll(identifier) : $super.ll.call(this, identifier);
  }
  toString() {
    return 'PermissionDefinedEvent(identifier=' + this.vn_1 + ')';
  }
  hashCode() {
    return getStringHashCode(this.vn_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof PermissionDefinedEvent))
      return false;
    if (!(this.vn_1 === other.vn_1))
      return false;
    return true;
  }
  static wn(seen0, identifier, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_14().un_1);
    }
    var $this = createThis(this);
    $this.vn_1 = identifier;
    return $this;
  }
  get identifier() {
    return this.ql();
  }
}
class PermissionDTO {}
class Companion_34 {
  constructor() {
    Companion_instance_34 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.xn_1 = [null, null, null, lazy(tmp_0, Permission$Companion$$childSerializers$_anonymous__yuaogr), null];
  }
}
class $serializer_15 {
  constructor() {
    $serializer_instance_15 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.permission.model.Permission', this, 5);
    tmp0_serialDesc.ki('id', false);
    tmp0_serialDesc.ki('identifier', false);
    tmp0_serialDesc.ki('description', false);
    tmp0_serialDesc.ki('features', false);
    tmp0_serialDesc.ki('type', true);
    this.yn_1 = tmp0_serialDesc;
  }
  ve() {
    return this.yn_1;
  }
  hi() {
    var tmp0_cached = Companion_getInstance_34().xn_1;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), StringSerializer_getInstance(), StringSerializer_getInstance(), get_nullable(tmp0_cached[3].d1()), StringSerializer_getInstance()];
  }
}
class Permission {
  constructor(id, identifier, description, features, type) {
    Companion_getInstance_34();
    type = type === VOID ? 'PERMISSION' : type;
    this.zn_1 = id;
    this.ao_1 = identifier;
    this.bo_1 = description;
    this.co_1 = features;
    this.do_1 = type;
  }
  ri() {
    return this.zn_1;
  }
  ql() {
    return this.ao_1;
  }
  gm() {
    return this.bo_1;
  }
  mn() {
    return this.co_1;
  }
  ti() {
    return this.do_1;
  }
  dd() {
    return this.zn_1;
  }
  ed() {
    return this.ao_1;
  }
  dm() {
    return this.bo_1;
  }
  um() {
    return this.co_1;
  }
  eo() {
    return this.do_1;
  }
  fo(id, identifier, description, features, type) {
    return new Permission(id, identifier, description, features, type);
  }
  copy(id, identifier, description, features, type, $super) {
    id = id === VOID ? this.zn_1 : id;
    identifier = identifier === VOID ? this.ao_1 : identifier;
    description = description === VOID ? this.bo_1 : description;
    features = features === VOID ? this.co_1 : features;
    type = type === VOID ? this.do_1 : type;
    return $super === VOID ? this.fo(id, identifier, description, features, type) : $super.fo.call(this, id, identifier, description, features, type);
  }
  toString() {
    return 'Permission(id=' + this.zn_1 + ', identifier=' + this.ao_1 + ', description=' + this.bo_1 + ', features=' + toString_0(this.co_1) + ', type=' + this.do_1 + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.zn_1);
    result = imul_0(result, 31) + getStringHashCode(this.ao_1) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.bo_1) | 0;
    result = imul_0(result, 31) + (this.co_1 == null ? 0 : hashCode(this.co_1)) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.do_1) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof Permission))
      return false;
    if (!(this.zn_1 === other.zn_1))
      return false;
    if (!(this.ao_1 === other.ao_1))
      return false;
    if (!(this.bo_1 === other.bo_1))
      return false;
    if (!equals(this.co_1, other.co_1))
      return false;
    if (!(this.do_1 === other.do_1))
      return false;
    return true;
  }
  static go(seen0, id, identifier, description, features, type, serializationConstructorMarker) {
    Companion_getInstance_34();
    if (!(15 === (15 & seen0))) {
      throwMissingFieldException(seen0, 15, $serializer_getInstance_15().yn_1);
    }
    var $this = createThis(this);
    $this.zn_1 = id;
    $this.ao_1 = identifier;
    $this.bo_1 = description;
    $this.co_1 = features;
    if (0 === (seen0 & 16))
      $this.do_1 = 'PERMISSION';
    else
      $this.do_1 = type;
    return $this;
  }
  get id() {
    return this.ri();
  }
  get identifier() {
    return this.ql();
  }
  get description() {
    return this.gm();
  }
  get features() {
    return this.mn();
  }
  get type() {
    return this.ti();
  }
}
class PermissionGetQueryDTO {}
class Companion_35 {}
class $serializer_16 {
  constructor() {
    $serializer_instance_16 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.permission.query.PermissionGetQuery', this, 1);
    tmp0_serialDesc.ki('identifier', false);
    this.ho_1 = tmp0_serialDesc;
  }
  ve() {
    return this.ho_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance()];
  }
}
class PermissionGetQuery {
  constructor(identifier) {
    this.io_1 = identifier;
  }
  ql() {
    return this.io_1;
  }
  dd() {
    return this.io_1;
  }
  ll(identifier) {
    return new PermissionGetQuery(identifier);
  }
  copy(identifier, $super) {
    identifier = identifier === VOID ? this.io_1 : identifier;
    return $super === VOID ? this.ll(identifier) : $super.ll.call(this, identifier);
  }
  toString() {
    return 'PermissionGetQuery(identifier=' + this.io_1 + ')';
  }
  hashCode() {
    return getStringHashCode(this.io_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof PermissionGetQuery))
      return false;
    if (!(this.io_1 === other.io_1))
      return false;
    return true;
  }
  static jo(seen0, identifier, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_16().ho_1);
    }
    var $this = createThis(this);
    $this.io_1 = identifier;
    return $this;
  }
  get identifier() {
    return this.ql();
  }
}
class PermissionGetResultDTO {}
class Companion_36 {}
class $serializer_17 {
  constructor() {
    $serializer_instance_17 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.permission.query.PermissionGetResult', this, 1);
    tmp0_serialDesc.ki('item', false);
    this.ko_1 = tmp0_serialDesc;
  }
  ve() {
    return this.ko_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [get_nullable($serializer_getInstance_15())];
  }
}
class PermissionGetResult {
  constructor(item) {
    this.lo_1 = item;
  }
  an() {
    return this.lo_1;
  }
  dd() {
    return this.lo_1;
  }
  mo(item) {
    return new PermissionGetResult(item);
  }
  copy(item, $super) {
    item = item === VOID ? this.lo_1 : item;
    return $super === VOID ? this.mo(item) : $super.mo.call(this, item);
  }
  toString() {
    return 'PermissionGetResult(item=' + toString_0(this.lo_1) + ')';
  }
  hashCode() {
    return this.lo_1 == null ? 0 : this.lo_1.hashCode();
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof PermissionGetResult))
      return false;
    if (!equals(this.lo_1, other.lo_1))
      return false;
    return true;
  }
  static no(seen0, item, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_17().ko_1);
    }
    var $this = createThis(this);
    $this.lo_1 = item;
    return $this;
  }
  get item() {
    return this.an();
  }
}
class PermissionListQueryDTO {}
class Companion_37 {}
class $serializer_18 {
  constructor() {
    $serializer_instance_18 = this;
    this.oo_1 = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.permission.query.PermissionListQuery', this, 0);
  }
  ve() {
    return this.oo_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [];
  }
}
class PermissionListQuery {
  static po(seen0, serializationConstructorMarker) {
    if (!(0 === (0 & seen0))) {
      throwMissingFieldException(seen0, 0, $serializer_getInstance_18().oo_1);
    }
    return createThis(this);
  }
}
class PermissionListResultDTO {}
class Companion_38 {
  constructor() {
    Companion_instance_38 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.qo_1 = [lazy(tmp_0, PermissionListResult$Companion$$childSerializers$_anonymous__atvy5c)];
  }
}
class $serializer_19 {
  constructor() {
    $serializer_instance_19 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.permission.query.PermissionListResult', this, 1);
    tmp0_serialDesc.ki('items', false);
    this.ro_1 = tmp0_serialDesc;
  }
  ve() {
    return this.ro_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [Companion_getInstance_38().qo_1[0].d1()];
  }
}
class PermissionListResult {
  constructor(items) {
    Companion_getInstance_38();
    this.so_1 = items;
  }
  jk() {
    return this.so_1;
  }
  dd() {
    return this.so_1;
  }
  to(items) {
    return new PermissionListResult(items);
  }
  copy(items, $super) {
    items = items === VOID ? this.so_1 : items;
    return $super === VOID ? this.to(items) : $super.to.call(this, items);
  }
  toString() {
    return 'PermissionListResult(items=' + toString_1(this.so_1) + ')';
  }
  hashCode() {
    return hashCode(this.so_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof PermissionListResult))
      return false;
    if (!equals(this.so_1, other.so_1))
      return false;
    return true;
  }
  static uo(seen0, items, serializationConstructorMarker) {
    Companion_getInstance_38();
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_19().ro_1);
    }
    var $this = createThis(this);
    $this.so_1 = items;
    return $this;
  }
  get items() {
    return this.jk();
  }
}
class RoleDefineCommandDTO {}
class Companion_39 {
  constructor() {
    Companion_instance_39 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    var tmp_1 = lazy(tmp_0, RoleDefineCommand$Companion$$childSerializers$_anonymous__bp9fbk);
    var tmp_2 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    var tmp_3 = lazy(tmp_2, RoleDefineCommand$Companion$$childSerializers$_anonymous__bp9fbk_0);
    var tmp_4 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    var tmp_5 = lazy(tmp_4, RoleDefineCommand$Companion$$childSerializers$_anonymous__bp9fbk_1);
    var tmp_6 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.zo_1 = [null, null, tmp_1, tmp_3, tmp_5, lazy(tmp_6, RoleDefineCommand$Companion$$childSerializers$_anonymous__bp9fbk_2)];
  }
}
class $serializer_20 {
  constructor() {
    $serializer_instance_20 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.role.command.RoleDefineCommand', this, 6);
    tmp0_serialDesc.ki('identifier', false);
    tmp0_serialDesc.ki('description', false);
    tmp0_serialDesc.ki('targets', false);
    tmp0_serialDesc.ki('locale', false);
    tmp0_serialDesc.ki('bindings', false);
    tmp0_serialDesc.ki('permissions', false);
    this.ap_1 = tmp0_serialDesc;
  }
  ve() {
    return this.ap_1;
  }
  hi() {
    var tmp0_cached = Companion_getInstance_39().zo_1;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), StringSerializer_getInstance(), tmp0_cached[2].d1(), tmp0_cached[3].d1(), get_nullable(tmp0_cached[4].d1()), get_nullable(tmp0_cached[5].d1())];
  }
}
class RoleDefineCommand {
  constructor(identifier, description, targets, locale, bindings, permissions) {
    Companion_getInstance_39();
    this.bp_1 = identifier;
    this.cp_1 = description;
    this.dp_1 = targets;
    this.ep_1 = locale;
    this.fp_1 = bindings;
    this.gp_1 = permissions;
  }
  ql() {
    return this.bp_1;
  }
  gm() {
    return this.cp_1;
  }
  vo() {
    return this.dp_1;
  }
  wo() {
    return this.ep_1;
  }
  xo() {
    return this.fp_1;
  }
  yo() {
    return this.gp_1;
  }
  dd() {
    return this.bp_1;
  }
  ed() {
    return this.cp_1;
  }
  dm() {
    return this.dp_1;
  }
  um() {
    return this.ep_1;
  }
  eo() {
    return this.fp_1;
  }
  hp() {
    return this.gp_1;
  }
  ip(identifier, description, targets, locale, bindings, permissions) {
    return new RoleDefineCommand(identifier, description, targets, locale, bindings, permissions);
  }
  copy(identifier, description, targets, locale, bindings, permissions, $super) {
    identifier = identifier === VOID ? this.bp_1 : identifier;
    description = description === VOID ? this.cp_1 : description;
    targets = targets === VOID ? this.dp_1 : targets;
    locale = locale === VOID ? this.ep_1 : locale;
    bindings = bindings === VOID ? this.fp_1 : bindings;
    permissions = permissions === VOID ? this.gp_1 : permissions;
    return $super === VOID ? this.ip(identifier, description, targets, locale, bindings, permissions) : $super.ip.call(this, identifier, description, targets, locale, bindings, permissions);
  }
  toString() {
    return 'RoleDefineCommand(identifier=' + this.bp_1 + ', description=' + this.cp_1 + ', targets=' + toString_1(this.dp_1) + ', locale=' + toString_1(this.ep_1) + ', bindings=' + toString_0(this.fp_1) + ', permissions=' + toString_0(this.gp_1) + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.bp_1);
    result = imul_0(result, 31) + getStringHashCode(this.cp_1) | 0;
    result = imul_0(result, 31) + hashCode(this.dp_1) | 0;
    result = imul_0(result, 31) + hashCode(this.ep_1) | 0;
    result = imul_0(result, 31) + (this.fp_1 == null ? 0 : hashCode(this.fp_1)) | 0;
    result = imul_0(result, 31) + (this.gp_1 == null ? 0 : hashCode(this.gp_1)) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof RoleDefineCommand))
      return false;
    if (!(this.bp_1 === other.bp_1))
      return false;
    if (!(this.cp_1 === other.cp_1))
      return false;
    if (!equals(this.dp_1, other.dp_1))
      return false;
    if (!equals(this.ep_1, other.ep_1))
      return false;
    if (!equals(this.fp_1, other.fp_1))
      return false;
    if (!equals(this.gp_1, other.gp_1))
      return false;
    return true;
  }
  static jp(seen0, identifier, description, targets, locale, bindings, permissions, serializationConstructorMarker) {
    Companion_getInstance_39();
    if (!(63 === (63 & seen0))) {
      throwMissingFieldException(seen0, 63, $serializer_getInstance_20().ap_1);
    }
    var $this = createThis(this);
    $this.bp_1 = identifier;
    $this.cp_1 = description;
    $this.dp_1 = targets;
    $this.ep_1 = locale;
    $this.fp_1 = bindings;
    $this.gp_1 = permissions;
    return $this;
  }
  get identifier() {
    return this.ql();
  }
  get description() {
    return this.gm();
  }
  get targets() {
    return this.vo();
  }
  get locale() {
    return this.wo();
  }
  get bindings() {
    return this.xo();
  }
  get permissions() {
    return this.yo();
  }
}
class RoleDefinedEventDTO {}
class Companion_40 {}
class $serializer_21 {
  constructor() {
    $serializer_instance_21 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.role.command.RoleDefinedEvent', this, 1);
    tmp0_serialDesc.ki('identifier', false);
    this.kp_1 = tmp0_serialDesc;
  }
  ve() {
    return this.kp_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance()];
  }
}
class RoleDefinedEvent {
  constructor(identifier) {
    this.lp_1 = identifier;
  }
  ql() {
    return this.lp_1;
  }
  dd() {
    return this.lp_1;
  }
  ll(identifier) {
    return new RoleDefinedEvent(identifier);
  }
  copy(identifier, $super) {
    identifier = identifier === VOID ? this.lp_1 : identifier;
    return $super === VOID ? this.ll(identifier) : $super.ll.call(this, identifier);
  }
  toString() {
    return 'RoleDefinedEvent(identifier=' + this.lp_1 + ')';
  }
  hashCode() {
    return getStringHashCode(this.lp_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof RoleDefinedEvent))
      return false;
    if (!(this.lp_1 === other.lp_1))
      return false;
    return true;
  }
  static mp(seen0, identifier, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_21().kp_1);
    }
    var $this = createThis(this);
    $this.lp_1 = identifier;
    return $this;
  }
  get identifier() {
    return this.ql();
  }
}
class RoleDTO {}
class Companion_41 {
  constructor() {
    Companion_instance_41 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    var tmp_1 = lazy(tmp_0, Role$Companion$$childSerializers$_anonymous__76f9e4);
    var tmp_2 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    var tmp_3 = lazy(tmp_2, Role$Companion$$childSerializers$_anonymous__76f9e4_0);
    var tmp_4 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    var tmp_5 = lazy(tmp_4, Role$Companion$$childSerializers$_anonymous__76f9e4_1);
    var tmp_6 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.np_1 = [null, null, null, tmp_1, tmp_3, tmp_5, lazy(tmp_6, Role$Companion$$childSerializers$_anonymous__76f9e4_2), null];
  }
}
class $serializer_22 {
  constructor() {
    $serializer_instance_22 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.role.model.Role', this, 8);
    tmp0_serialDesc.ki('id', false);
    tmp0_serialDesc.ki('identifier', false);
    tmp0_serialDesc.ki('description', false);
    tmp0_serialDesc.ki('targets', false);
    tmp0_serialDesc.ki('locale', false);
    tmp0_serialDesc.ki('bindings', false);
    tmp0_serialDesc.ki('permissions', false);
    tmp0_serialDesc.ki('type', true);
    this.op_1 = tmp0_serialDesc;
  }
  ve() {
    return this.op_1;
  }
  hi() {
    var tmp0_cached = Companion_getInstance_41().np_1;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), StringSerializer_getInstance(), StringSerializer_getInstance(), tmp0_cached[3].d1(), tmp0_cached[4].d1(), tmp0_cached[5].d1(), tmp0_cached[6].d1(), StringSerializer_getInstance()];
  }
}
class Role {
  constructor(id, identifier, description, targets, locale, bindings, permissions, type) {
    Companion_getInstance_41();
    type = type === VOID ? 'ROLE' : type;
    this.pp_1 = id;
    this.qp_1 = identifier;
    this.rp_1 = description;
    this.sp_1 = targets;
    this.tp_1 = locale;
    this.up_1 = bindings;
    this.vp_1 = permissions;
    this.wp_1 = type;
  }
  ri() {
    return this.pp_1;
  }
  ql() {
    return this.qp_1;
  }
  gm() {
    return this.rp_1;
  }
  vo() {
    return this.sp_1;
  }
  wo() {
    return this.tp_1;
  }
  xo() {
    return this.up_1;
  }
  yo() {
    return this.vp_1;
  }
  ti() {
    return this.wp_1;
  }
  dd() {
    return this.pp_1;
  }
  ed() {
    return this.qp_1;
  }
  dm() {
    return this.rp_1;
  }
  um() {
    return this.sp_1;
  }
  eo() {
    return this.tp_1;
  }
  hp() {
    return this.up_1;
  }
  xp() {
    return this.vp_1;
  }
  yp() {
    return this.wp_1;
  }
  zp(id, identifier, description, targets, locale, bindings, permissions, type) {
    return new Role(id, identifier, description, targets, locale, bindings, permissions, type);
  }
  copy(id, identifier, description, targets, locale, bindings, permissions, type, $super) {
    id = id === VOID ? this.pp_1 : id;
    identifier = identifier === VOID ? this.qp_1 : identifier;
    description = description === VOID ? this.rp_1 : description;
    targets = targets === VOID ? this.sp_1 : targets;
    locale = locale === VOID ? this.tp_1 : locale;
    bindings = bindings === VOID ? this.up_1 : bindings;
    permissions = permissions === VOID ? this.vp_1 : permissions;
    type = type === VOID ? this.wp_1 : type;
    return $super === VOID ? this.zp(id, identifier, description, targets, locale, bindings, permissions, type) : $super.zp.call(this, id, identifier, description, targets, locale, bindings, permissions, type);
  }
  toString() {
    return 'Role(id=' + this.pp_1 + ', identifier=' + this.qp_1 + ', description=' + this.rp_1 + ', targets=' + toString_1(this.sp_1) + ', locale=' + toString_1(this.tp_1) + ', bindings=' + toString_1(this.up_1) + ', permissions=' + toString_1(this.vp_1) + ', type=' + this.wp_1 + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.pp_1);
    result = imul_0(result, 31) + getStringHashCode(this.qp_1) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.rp_1) | 0;
    result = imul_0(result, 31) + hashCode(this.sp_1) | 0;
    result = imul_0(result, 31) + hashCode(this.tp_1) | 0;
    result = imul_0(result, 31) + hashCode(this.up_1) | 0;
    result = imul_0(result, 31) + hashCode(this.vp_1) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.wp_1) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof Role))
      return false;
    if (!(this.pp_1 === other.pp_1))
      return false;
    if (!(this.qp_1 === other.qp_1))
      return false;
    if (!(this.rp_1 === other.rp_1))
      return false;
    if (!equals(this.sp_1, other.sp_1))
      return false;
    if (!equals(this.tp_1, other.tp_1))
      return false;
    if (!equals(this.up_1, other.up_1))
      return false;
    if (!equals(this.vp_1, other.vp_1))
      return false;
    if (!(this.wp_1 === other.wp_1))
      return false;
    return true;
  }
  static aq(seen0, id, identifier, description, targets, locale, bindings, permissions, type, serializationConstructorMarker) {
    Companion_getInstance_41();
    if (!(127 === (127 & seen0))) {
      throwMissingFieldException(seen0, 127, $serializer_getInstance_22().op_1);
    }
    var $this = createThis(this);
    $this.pp_1 = id;
    $this.qp_1 = identifier;
    $this.rp_1 = description;
    $this.sp_1 = targets;
    $this.tp_1 = locale;
    $this.up_1 = bindings;
    $this.vp_1 = permissions;
    if (0 === (seen0 & 128))
      $this.wp_1 = 'ROLE';
    else
      $this.wp_1 = type;
    return $this;
  }
  get id() {
    return this.ri();
  }
  get identifier() {
    return this.ql();
  }
  get description() {
    return this.gm();
  }
  get targets() {
    return this.vo();
  }
  get locale() {
    return this.wo();
  }
  get bindings() {
    return this.xo();
  }
  get permissions() {
    return this.yo();
  }
  get type() {
    return this.ti();
  }
}
class RoleTargetValues {
  constructor() {
    this.ORGANIZATION = 'ORGANIZATION';
    this.USER = 'USER';
    this.API_KEY = 'API_KEY';
  }
  bq() {
    return this.ORGANIZATION;
  }
  cq() {
    return this.USER;
  }
  dq() {
    return this.API_KEY;
  }
}
class RoleGetQueryDTO {}
class Companion_42 {}
class $serializer_23 {
  constructor() {
    $serializer_instance_23 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.role.query.RoleGetQuery', this, 1);
    tmp0_serialDesc.ki('identifier', false);
    this.eq_1 = tmp0_serialDesc;
  }
  ve() {
    return this.eq_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance()];
  }
}
class RoleGetQuery {
  constructor(identifier) {
    this.fq_1 = identifier;
  }
  ql() {
    return this.fq_1;
  }
  dd() {
    return this.fq_1;
  }
  ll(identifier) {
    return new RoleGetQuery(identifier);
  }
  copy(identifier, $super) {
    identifier = identifier === VOID ? this.fq_1 : identifier;
    return $super === VOID ? this.ll(identifier) : $super.ll.call(this, identifier);
  }
  toString() {
    return 'RoleGetQuery(identifier=' + this.fq_1 + ')';
  }
  hashCode() {
    return getStringHashCode(this.fq_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof RoleGetQuery))
      return false;
    if (!(this.fq_1 === other.fq_1))
      return false;
    return true;
  }
  static gq(seen0, identifier, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_23().eq_1);
    }
    var $this = createThis(this);
    $this.fq_1 = identifier;
    return $this;
  }
  get identifier() {
    return this.ql();
  }
}
class RoleGetResultDTO {}
class Companion_43 {}
class $serializer_24 {
  constructor() {
    $serializer_instance_24 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.role.query.RoleGetResult', this, 1);
    tmp0_serialDesc.ki('item', false);
    this.hq_1 = tmp0_serialDesc;
  }
  ve() {
    return this.hq_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [get_nullable($serializer_getInstance_22())];
  }
}
class RoleGetResult {
  constructor(item) {
    this.iq_1 = item;
  }
  an() {
    return this.iq_1;
  }
  dd() {
    return this.iq_1;
  }
  jq(item) {
    return new RoleGetResult(item);
  }
  copy(item, $super) {
    item = item === VOID ? this.iq_1 : item;
    return $super === VOID ? this.jq(item) : $super.jq.call(this, item);
  }
  toString() {
    return 'RoleGetResult(item=' + toString_0(this.iq_1) + ')';
  }
  hashCode() {
    return this.iq_1 == null ? 0 : this.iq_1.hashCode();
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof RoleGetResult))
      return false;
    if (!equals(this.iq_1, other.iq_1))
      return false;
    return true;
  }
  static kq(seen0, item, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_24().hq_1);
    }
    var $this = createThis(this);
    $this.iq_1 = item;
    return $this;
  }
  get item() {
    return this.an();
  }
}
class RoleListQueryDTO {}
class Companion_44 {}
class $serializer_25 {
  constructor() {
    $serializer_instance_25 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.role.query.RoleListQuery', this, 1);
    tmp0_serialDesc.ki('target', false);
    this.mq_1 = tmp0_serialDesc;
  }
  ve() {
    return this.mq_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [get_nullable(StringSerializer_getInstance())];
  }
}
class RoleListQuery {
  constructor(target) {
    this.nq_1 = target;
  }
  lq() {
    return this.nq_1;
  }
  dd() {
    return this.nq_1;
  }
  oq(target) {
    return new RoleListQuery(target);
  }
  copy(target, $super) {
    target = target === VOID ? this.nq_1 : target;
    return $super === VOID ? this.oq(target) : $super.oq.call(this, target);
  }
  toString() {
    return 'RoleListQuery(target=' + this.nq_1 + ')';
  }
  hashCode() {
    return this.nq_1 == null ? 0 : getStringHashCode(this.nq_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof RoleListQuery))
      return false;
    if (!(this.nq_1 == other.nq_1))
      return false;
    return true;
  }
  static pq(seen0, target, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_25().mq_1);
    }
    var $this = createThis(this);
    $this.nq_1 = target;
    return $this;
  }
  get target() {
    return this.lq();
  }
}
class RoleListResultDTO {}
class Companion_45 {
  constructor() {
    Companion_instance_45 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.qq_1 = [lazy(tmp_0, RoleListResult$Companion$$childSerializers$_anonymous__t7zd89)];
  }
}
class $serializer_26 {
  constructor() {
    $serializer_instance_26 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.privilege.domain.role.query.RoleListResult', this, 1);
    tmp0_serialDesc.ki('items', false);
    this.rq_1 = tmp0_serialDesc;
  }
  ve() {
    return this.rq_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [Companion_getInstance_45().qq_1[0].d1()];
  }
}
class RoleListResult {
  constructor(items) {
    Companion_getInstance_45();
    this.sq_1 = items;
  }
  jk() {
    return this.sq_1;
  }
  dd() {
    return this.sq_1;
  }
  tq(items) {
    return new RoleListResult(items);
  }
  copy(items, $super) {
    items = items === VOID ? this.sq_1 : items;
    return $super === VOID ? this.tq(items) : $super.tq.call(this, items);
  }
  toString() {
    return 'RoleListResult(items=' + toString_1(this.sq_1) + ')';
  }
  hashCode() {
    return hashCode(this.sq_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof RoleListResult))
      return false;
    if (!equals(this.sq_1, other.sq_1))
      return false;
    return true;
  }
  static uq(seen0, items, serializationConstructorMarker) {
    Companion_getInstance_45();
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_26().rq_1);
    }
    var $this = createThis(this);
    $this.sq_1 = items;
    return $this;
  }
  get items() {
    return this.jk();
  }
}
class asFlow$$inlined$unsafeFlow$1 {
  constructor($this_asFlow) {
    this.vq_1 = $this_asFlow;
  }
  *wq(collector, $completion) {
    // Inline function 'kotlin.collections.forEach' call
    var _iterator__ex2g4s = this.vq_1.f();
    while (_iterator__ex2g4s.g()) {
      var element = _iterator__ex2g4s.h();
      yield* collector.xq(element, $completion);
    }
    return Unit_instance;
  }
  yq(collector, $completion) {
    return this.wq(collector, $completion);
  }
}
class FlowCollector {}
class sam$kotlinx_coroutines_flow_FlowCollector$0 {
  constructor(function_0) {
    this.zq_1 = function_0;
  }
  *xq(value, $completion) {
    return yield* $(this.zq_1(value, $completion), $completion);
  }
  r1() {
    return this.zq_1;
  }
  equals(other) {
    var tmp;
    if (!(other == null) ? isInterface(other, FlowCollector) : false) {
      var tmp_0;
      if (!(other == null) ? isInterface(other, FunctionAdapter) : false) {
        tmp_0 = equals(this.r1(), other.r1());
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
    return hashCode(this.r1());
  }
}
class F2Consumer {}
class F2Function {}
class F2Supplier {}
class F2SupplierSingle {}
class Companion_46 {}
class $serializer_27 {
  constructor() {
    $serializer_instance_27 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.dsl.fnc.operators.Batch', this, 2);
    tmp0_serialDesc.ki('size', true);
    tmp0_serialDesc.ki('concurrency', true);
    this.dr_1 = tmp0_serialDesc;
  }
  ve() {
    return this.dr_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [IntSerializer_getInstance(), IntSerializer_getInstance()];
  }
}
class Batch {
  constructor(size, concurrency) {
    size = size === VOID ? 128 : size;
    concurrency = concurrency === VOID ? get_BATCH_DEFAULT_CONCURRENCY() : concurrency;
    this.size = size;
    this.concurrency = concurrency;
  }
  i() {
    return this.size;
  }
  er() {
    return this.concurrency;
  }
  dd() {
    return this.size;
  }
  ed() {
    return this.concurrency;
  }
  fr(size, concurrency) {
    return new Batch(size, concurrency);
  }
  copy(size, concurrency, $super) {
    size = size === VOID ? this.size : size;
    concurrency = concurrency === VOID ? this.concurrency : concurrency;
    return $super === VOID ? this.fr(size, concurrency) : $super.fr.call(this, size, concurrency);
  }
  toString() {
    return 'Batch(size=' + this.size + ', concurrency=' + this.concurrency + ')';
  }
  hashCode() {
    var result = this.size;
    result = imul_0(result, 31) + this.concurrency | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof Batch))
      return false;
    if (!(this.size === other.size))
      return false;
    if (!(this.concurrency === other.concurrency))
      return false;
    return true;
  }
  static gr(seen0, size, concurrency, serializationConstructorMarker) {
    if (!(0 === (0 & seen0))) {
      throwMissingFieldException(seen0, 0, $serializer_getInstance_27().dr_1);
    }
    var $this = createThis(this);
    if (0 === (seen0 & 1))
      $this.size = 128;
    else
      $this.size = size;
    if (0 === (seen0 & 2))
      $this.concurrency = get_BATCH_DEFAULT_CONCURRENCY();
    else
      $this.concurrency = concurrency;
    return $this;
  }
}
class AuthRealm {
  constructor(serverUrl, realmId, clientId, redirectUrl) {
    this.hr_1 = serverUrl;
    this.ir_1 = realmId;
    this.jr_1 = clientId;
    this.kr_1 = redirectUrl;
  }
  lr() {
    return this.hr_1;
  }
  mr() {
    return this.ir_1;
  }
  nr() {
    return this.jr_1;
  }
  pr() {
    return this.kr_1;
  }
  get serverUrl() {
    return this.lr();
  }
  get realmId() {
    return this.mr();
  }
  get clientId() {
    return this.nr();
  }
  get redirectUrl() {
    return this.pr();
  }
}
class AuthRealmPassword extends AuthRealm {
  constructor(serverUrl, realmId, redirectUrl, clientId, username, password) {
    super(serverUrl, realmId, clientId, redirectUrl);
    this.ur_1 = serverUrl;
    this.vr_1 = realmId;
    this.wr_1 = redirectUrl;
    this.xr_1 = clientId;
    this.username = username;
    this.password = password;
  }
  lr() {
    return this.ur_1;
  }
  mr() {
    return this.vr_1;
  }
  pr() {
    return this.wr_1;
  }
  nr() {
    return this.xr_1;
  }
  yr() {
    return this.username;
  }
  zr() {
    return this.password;
  }
  dd() {
    return this.ur_1;
  }
  ed() {
    return this.vr_1;
  }
  dm() {
    return this.wr_1;
  }
  um() {
    return this.xr_1;
  }
  eo() {
    return this.username;
  }
  hp() {
    return this.password;
  }
  as(serverUrl, realmId, redirectUrl, clientId, username, password) {
    return new AuthRealmPassword(serverUrl, realmId, redirectUrl, clientId, username, password);
  }
  copy(serverUrl, realmId, redirectUrl, clientId, username, password, $super) {
    serverUrl = serverUrl === VOID ? this.ur_1 : serverUrl;
    realmId = realmId === VOID ? this.vr_1 : realmId;
    redirectUrl = redirectUrl === VOID ? this.wr_1 : redirectUrl;
    clientId = clientId === VOID ? this.xr_1 : clientId;
    username = username === VOID ? this.username : username;
    password = password === VOID ? this.password : password;
    return $super === VOID ? this.as(serverUrl, realmId, redirectUrl, clientId, username, password) : $super.as.call(this, serverUrl, realmId, redirectUrl, clientId, username, password);
  }
  toString() {
    return 'AuthRealmPassword(serverUrl=' + this.ur_1 + ', realmId=' + this.vr_1 + ', redirectUrl=' + this.wr_1 + ', clientId=' + this.xr_1 + ', username=' + this.username + ', password=' + this.password + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.ur_1);
    result = imul_0(result, 31) + getStringHashCode(this.vr_1) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.wr_1) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.xr_1) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.username) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.password) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof AuthRealmPassword))
      return false;
    if (!(this.ur_1 === other.ur_1))
      return false;
    if (!(this.vr_1 === other.vr_1))
      return false;
    if (!(this.wr_1 === other.wr_1))
      return false;
    if (!(this.xr_1 === other.xr_1))
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
    this.fs_1 = serverUrl;
    this.gs_1 = realmId;
    this.hs_1 = clientId;
    this.is_1 = redirectUrl;
    this.clientSecret = clientSecret;
    this.isPublic = isPublic;
  }
  lr() {
    return this.fs_1;
  }
  mr() {
    return this.gs_1;
  }
  nr() {
    return this.hs_1;
  }
  pr() {
    return this.is_1;
  }
  js() {
    return this.clientSecret;
  }
  ks() {
    return this.isPublic;
  }
  dd() {
    return this.fs_1;
  }
  ed() {
    return this.gs_1;
  }
  dm() {
    return this.hs_1;
  }
  um() {
    return this.is_1;
  }
  eo() {
    return this.clientSecret;
  }
  hp() {
    return this.isPublic;
  }
  ls(serverUrl, realmId, clientId, redirectUrl, clientSecret, isPublic) {
    return new AuthRealmClientSecret(serverUrl, realmId, clientId, redirectUrl, clientSecret, isPublic);
  }
  copy(serverUrl, realmId, clientId, redirectUrl, clientSecret, isPublic, $super) {
    serverUrl = serverUrl === VOID ? this.fs_1 : serverUrl;
    realmId = realmId === VOID ? this.gs_1 : realmId;
    clientId = clientId === VOID ? this.hs_1 : clientId;
    redirectUrl = redirectUrl === VOID ? this.is_1 : redirectUrl;
    clientSecret = clientSecret === VOID ? this.clientSecret : clientSecret;
    isPublic = isPublic === VOID ? this.isPublic : isPublic;
    return $super === VOID ? this.ls(serverUrl, realmId, clientId, redirectUrl, clientSecret, isPublic) : $super.ls.call(this, serverUrl, realmId, clientId, redirectUrl, clientSecret, isPublic);
  }
  toString() {
    return 'AuthRealmClientSecret(serverUrl=' + this.fs_1 + ', realmId=' + this.gs_1 + ', clientId=' + this.hs_1 + ', redirectUrl=' + this.is_1 + ', clientSecret=' + this.clientSecret + ', isPublic=' + this.isPublic + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.fs_1);
    result = imul_0(result, 31) + getStringHashCode(this.gs_1) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.hs_1) | 0;
    result = imul_0(result, 31) + (this.is_1 == null ? 0 : getStringHashCode(this.is_1)) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.clientSecret) | 0;
    result = imul_0(result, 31) + getBooleanHashCode(this.isPublic) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof AuthRealmClientSecret))
      return false;
    if (!(this.fs_1 === other.fs_1))
      return false;
    if (!(this.gs_1 === other.gs_1))
      return false;
    if (!(this.hs_1 === other.hs_1))
      return false;
    if (!(this.is_1 == other.is_1))
      return false;
    if (!(this.clientSecret === other.clientSecret))
      return false;
    if (!(this.isPublic === other.isPublic))
      return false;
    return true;
  }
}
class Companion_47 {}
class $serializer_28 {
  constructor() {
    $serializer_instance_28 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('f2.client.domain.TokenInfo', this, 7);
    tmp0_serialDesc.ki('access_token', false);
    tmp0_serialDesc.ki('expires_in', false);
    tmp0_serialDesc.ki('refresh_expires_in', true);
    tmp0_serialDesc.ki('token_type', false);
    tmp0_serialDesc.ki('scope', false);
    tmp0_serialDesc.ki('id_token', true);
    tmp0_serialDesc.ki('refresh_token', true);
    this.ms_1 = tmp0_serialDesc;
  }
  ve() {
    return this.ms_1;
  }
  hi() {
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
  ns() {
    return this.accessToken;
  }
  os() {
    return this.expiresIn;
  }
  ps() {
    return this.refreshExpiresIn;
  }
  qs() {
    return this.tokenType;
  }
  rs() {
    return this.scope;
  }
  ss() {
    return this.idToken;
  }
  ts() {
    return this.refreshToken;
  }
  dd() {
    return this.accessToken;
  }
  ed() {
    return this.expiresIn;
  }
  dm() {
    return this.refreshExpiresIn;
  }
  um() {
    return this.tokenType;
  }
  eo() {
    return this.scope;
  }
  hp() {
    return this.idToken;
  }
  xp() {
    return this.refreshToken;
  }
  us(accessToken, expiresIn, refreshExpiresIn, tokenType, scope, idToken, refreshToken) {
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
    return $super === VOID ? this.us(accessToken, expiresIn, refreshExpiresIn, tokenType, scope, idToken, refreshToken) : $super.us.call(this, accessToken, expiresIn, refreshExpiresIn, tokenType, scope, idToken, refreshToken);
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
  static vs(seen0, accessToken, expiresIn, refreshExpiresIn, tokenType, scope, idToken, refreshToken, serializationConstructorMarker) {
    if (!(27 === (27 & seen0))) {
      throwMissingFieldException(seen0, 27, $serializer_getInstance_28().ms_1);
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
class OrganizationCreateCommandDTO {}
class Companion_48 {
  constructor() {
    Companion_instance_48 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    var tmp_1 = lazy(tmp_0, OrganizationCreateCommand$Companion$$childSerializers$_anonymous__9qbm7i);
    var tmp_2 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.ct_1 = [null, null, null, null, null, tmp_1, null, lazy(tmp_2, OrganizationCreateCommand$Companion$$childSerializers$_anonymous__9qbm7i_0), null];
  }
}
class $serializer_29 {
  constructor() {
    $serializer_instance_29 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.command.OrganizationCreateCommand', this, 9);
    tmp0_serialDesc.ki('siret', true);
    tmp0_serialDesc.ki('name', false);
    tmp0_serialDesc.ki('description', true);
    tmp0_serialDesc.ki('address', true);
    tmp0_serialDesc.ki('website', true);
    tmp0_serialDesc.ki('roles', true);
    tmp0_serialDesc.ki('parentOrganizationId', true);
    tmp0_serialDesc.ki('attributes', true);
    tmp0_serialDesc.ki('status', true);
    this.dt_1 = tmp0_serialDesc;
  }
  ve() {
    return this.dt_1;
  }
  hi() {
    var tmp0_cached = Companion_getInstance_48().ct_1;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [get_nullable(StringSerializer_getInstance()), StringSerializer_getInstance(), get_nullable(StringSerializer_getInstance()), get_nullable($serializer_getInstance_5()), get_nullable(StringSerializer_getInstance()), get_nullable(tmp0_cached[5].d1()), get_nullable(StringSerializer_getInstance()), get_nullable(tmp0_cached[7].d1()), StringSerializer_getInstance()];
  }
}
class OrganizationCreateCommand {
  constructor(siret, name, description, address, website, roles, parentOrganizationId, attributes, status) {
    Companion_getInstance_48();
    siret = siret === VOID ? null : siret;
    description = description === VOID ? null : description;
    address = address === VOID ? null : address;
    website = website === VOID ? null : website;
    roles = roles === VOID ? null : roles;
    parentOrganizationId = parentOrganizationId === VOID ? null : parentOrganizationId;
    attributes = attributes === VOID ? null : attributes;
    status = status === VOID ? OrganizationStatusValues_instance.pending() : status;
    this.et_1 = siret;
    this.ft_1 = name;
    this.gt_1 = description;
    this.ht_1 = address;
    this.it_1 = website;
    this.jt_1 = roles;
    this.kt_1 = parentOrganizationId;
    this.lt_1 = attributes;
    this.mt_1 = status;
  }
  ws() {
    return this.et_1;
  }
  n1() {
    return this.ft_1;
  }
  gm() {
    return this.gt_1;
  }
  xs() {
    return this.ht_1;
  }
  ys() {
    return this.it_1;
  }
  sl() {
    return this.jt_1;
  }
  zs() {
    return this.kt_1;
  }
  at() {
    return this.lt_1;
  }
  bt() {
    return this.mt_1;
  }
  dd() {
    return this.et_1;
  }
  ed() {
    return this.ft_1;
  }
  dm() {
    return this.gt_1;
  }
  um() {
    return this.ht_1;
  }
  eo() {
    return this.it_1;
  }
  hp() {
    return this.jt_1;
  }
  xp() {
    return this.kt_1;
  }
  yp() {
    return this.lt_1;
  }
  nt() {
    return this.mt_1;
  }
  ot(siret, name, description, address, website, roles, parentOrganizationId, attributes, status) {
    return new OrganizationCreateCommand(siret, name, description, address, website, roles, parentOrganizationId, attributes, status);
  }
  copy(siret, name, description, address, website, roles, parentOrganizationId, attributes, status, $super) {
    siret = siret === VOID ? this.et_1 : siret;
    name = name === VOID ? this.ft_1 : name;
    description = description === VOID ? this.gt_1 : description;
    address = address === VOID ? this.ht_1 : address;
    website = website === VOID ? this.it_1 : website;
    roles = roles === VOID ? this.jt_1 : roles;
    parentOrganizationId = parentOrganizationId === VOID ? this.kt_1 : parentOrganizationId;
    attributes = attributes === VOID ? this.lt_1 : attributes;
    status = status === VOID ? this.mt_1 : status;
    return $super === VOID ? this.ot(siret, name, description, address, website, roles, parentOrganizationId, attributes, status) : $super.ot.call(this, siret, name, description, address, website, roles, parentOrganizationId, attributes, status);
  }
  toString() {
    return 'OrganizationCreateCommand(siret=' + this.et_1 + ', name=' + this.ft_1 + ', description=' + this.gt_1 + ', address=' + toString_0(this.ht_1) + ', website=' + this.it_1 + ', roles=' + toString_0(this.jt_1) + ', parentOrganizationId=' + this.kt_1 + ', attributes=' + toString_0(this.lt_1) + ', status=' + this.mt_1 + ')';
  }
  hashCode() {
    var result = this.et_1 == null ? 0 : getStringHashCode(this.et_1);
    result = imul_0(result, 31) + getStringHashCode(this.ft_1) | 0;
    result = imul_0(result, 31) + (this.gt_1 == null ? 0 : getStringHashCode(this.gt_1)) | 0;
    result = imul_0(result, 31) + (this.ht_1 == null ? 0 : this.ht_1.hashCode()) | 0;
    result = imul_0(result, 31) + (this.it_1 == null ? 0 : getStringHashCode(this.it_1)) | 0;
    result = imul_0(result, 31) + (this.jt_1 == null ? 0 : hashCode(this.jt_1)) | 0;
    result = imul_0(result, 31) + (this.kt_1 == null ? 0 : getStringHashCode(this.kt_1)) | 0;
    result = imul_0(result, 31) + (this.lt_1 == null ? 0 : hashCode(this.lt_1)) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.mt_1) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationCreateCommand))
      return false;
    if (!(this.et_1 == other.et_1))
      return false;
    if (!(this.ft_1 === other.ft_1))
      return false;
    if (!(this.gt_1 == other.gt_1))
      return false;
    if (!equals(this.ht_1, other.ht_1))
      return false;
    if (!(this.it_1 == other.it_1))
      return false;
    if (!equals(this.jt_1, other.jt_1))
      return false;
    if (!(this.kt_1 == other.kt_1))
      return false;
    if (!equals(this.lt_1, other.lt_1))
      return false;
    if (!(this.mt_1 === other.mt_1))
      return false;
    return true;
  }
  static pt(seen0, siret, name, description, address, website, roles, parentOrganizationId, attributes, status, serializationConstructorMarker) {
    Companion_getInstance_48();
    if (!(2 === (2 & seen0))) {
      throwMissingFieldException(seen0, 2, $serializer_getInstance_29().dt_1);
    }
    var $this = createThis(this);
    if (0 === (seen0 & 1))
      $this.et_1 = null;
    else
      $this.et_1 = siret;
    $this.ft_1 = name;
    if (0 === (seen0 & 4))
      $this.gt_1 = null;
    else
      $this.gt_1 = description;
    if (0 === (seen0 & 8))
      $this.ht_1 = null;
    else
      $this.ht_1 = address;
    if (0 === (seen0 & 16))
      $this.it_1 = null;
    else
      $this.it_1 = website;
    if (0 === (seen0 & 32))
      $this.jt_1 = null;
    else
      $this.jt_1 = roles;
    if (0 === (seen0 & 64))
      $this.kt_1 = null;
    else
      $this.kt_1 = parentOrganizationId;
    if (0 === (seen0 & 128))
      $this.lt_1 = null;
    else
      $this.lt_1 = attributes;
    if (0 === (seen0 & 256))
      $this.mt_1 = OrganizationStatusValues_instance.pending();
    else
      $this.mt_1 = status;
    return $this;
  }
  get siret() {
    return this.ws();
  }
  get name() {
    return this.n1();
  }
  get description() {
    return this.gm();
  }
  get address() {
    return this.xs();
  }
  get website() {
    return this.ys();
  }
  get roles() {
    return this.sl();
  }
  get parentOrganizationId() {
    return this.zs();
  }
  get attributes() {
    return this.at();
  }
  get status() {
    return this.bt();
  }
}
class OrganizationCreatedEventDTO {}
class Companion_49 {}
class $serializer_30 {
  constructor() {
    $serializer_instance_30 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.command.OrganizationCreatedEvent', this, 2);
    tmp0_serialDesc.ki('id', false);
    tmp0_serialDesc.ki('parentOrganization', true);
    this.rt_1 = tmp0_serialDesc;
  }
  ve() {
    return this.rt_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), get_nullable(StringSerializer_getInstance())];
  }
}
class OrganizationCreatedEvent {
  constructor(id, parentOrganization) {
    parentOrganization = parentOrganization === VOID ? null : parentOrganization;
    this.st_1 = id;
    this.tt_1 = parentOrganization;
  }
  ri() {
    return this.st_1;
  }
  qt() {
    return this.tt_1;
  }
  dd() {
    return this.st_1;
  }
  ed() {
    return this.tt_1;
  }
  ut(id, parentOrganization) {
    return new OrganizationCreatedEvent(id, parentOrganization);
  }
  copy(id, parentOrganization, $super) {
    id = id === VOID ? this.st_1 : id;
    parentOrganization = parentOrganization === VOID ? this.tt_1 : parentOrganization;
    return $super === VOID ? this.ut(id, parentOrganization) : $super.ut.call(this, id, parentOrganization);
  }
  toString() {
    return 'OrganizationCreatedEvent(id=' + this.st_1 + ', parentOrganization=' + this.tt_1 + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.st_1);
    result = imul_0(result, 31) + (this.tt_1 == null ? 0 : getStringHashCode(this.tt_1)) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationCreatedEvent))
      return false;
    if (!(this.st_1 === other.st_1))
      return false;
    if (!(this.tt_1 == other.tt_1))
      return false;
    return true;
  }
  static vt(seen0, id, parentOrganization, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_30().rt_1);
    }
    var $this = createThis(this);
    $this.st_1 = id;
    if (0 === (seen0 & 2))
      $this.tt_1 = null;
    else
      $this.tt_1 = parentOrganization;
    return $this;
  }
  get id() {
    return this.ri();
  }
  get parentOrganization() {
    return this.qt();
  }
}
class OrganizationDeleteCommandDTO {}
class OrganizationDeletedEventDTO {}
class OrganizationDisableCommandDTO {}
class Companion_50 {
  constructor() {
    Companion_instance_50 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    var tmp_1 = lazy(tmp_0, OrganizationDisableCommand$Companion$$childSerializers$_anonymous__hrk94c);
    var tmp_2 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.zt_1 = [null, null, null, tmp_1, lazy(tmp_2, OrganizationDisableCommand$Companion$$childSerializers$_anonymous__hrk94c_0)];
  }
}
class $serializer_31 {
  constructor() {
    $serializer_instance_31 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.command.OrganizationDisableCommand', this, 5);
    tmp0_serialDesc.ki('id', false);
    tmp0_serialDesc.ki('disabledBy', false);
    tmp0_serialDesc.ki('anonymize', false);
    tmp0_serialDesc.ki('attributes', false);
    tmp0_serialDesc.ki('userAttributes', false);
    this.au_1 = tmp0_serialDesc;
  }
  ve() {
    return this.au_1;
  }
  hi() {
    var tmp0_cached = Companion_getInstance_50().zt_1;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), get_nullable(StringSerializer_getInstance()), BooleanSerializer_getInstance(), get_nullable(tmp0_cached[3].d1()), get_nullable(tmp0_cached[4].d1())];
  }
}
class OrganizationDisableCommand {
  constructor(id, disabledBy, anonymize, attributes, userAttributes) {
    Companion_getInstance_50();
    this.bu_1 = id;
    this.cu_1 = disabledBy;
    this.du_1 = anonymize;
    this.eu_1 = attributes;
    this.fu_1 = userAttributes;
  }
  ri() {
    return this.bu_1;
  }
  wt() {
    return this.cu_1;
  }
  xt() {
    return this.du_1;
  }
  at() {
    return this.eu_1;
  }
  yt() {
    return this.fu_1;
  }
  dd() {
    return this.bu_1;
  }
  ed() {
    return this.cu_1;
  }
  dm() {
    return this.du_1;
  }
  um() {
    return this.eu_1;
  }
  eo() {
    return this.fu_1;
  }
  gu(id, disabledBy, anonymize, attributes, userAttributes) {
    return new OrganizationDisableCommand(id, disabledBy, anonymize, attributes, userAttributes);
  }
  copy(id, disabledBy, anonymize, attributes, userAttributes, $super) {
    id = id === VOID ? this.bu_1 : id;
    disabledBy = disabledBy === VOID ? this.cu_1 : disabledBy;
    anonymize = anonymize === VOID ? this.du_1 : anonymize;
    attributes = attributes === VOID ? this.eu_1 : attributes;
    userAttributes = userAttributes === VOID ? this.fu_1 : userAttributes;
    return $super === VOID ? this.gu(id, disabledBy, anonymize, attributes, userAttributes) : $super.gu.call(this, id, disabledBy, anonymize, attributes, userAttributes);
  }
  toString() {
    return 'OrganizationDisableCommand(id=' + this.bu_1 + ', disabledBy=' + this.cu_1 + ', anonymize=' + this.du_1 + ', attributes=' + toString_0(this.eu_1) + ', userAttributes=' + toString_0(this.fu_1) + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.bu_1);
    result = imul_0(result, 31) + (this.cu_1 == null ? 0 : getStringHashCode(this.cu_1)) | 0;
    result = imul_0(result, 31) + getBooleanHashCode(this.du_1) | 0;
    result = imul_0(result, 31) + (this.eu_1 == null ? 0 : hashCode(this.eu_1)) | 0;
    result = imul_0(result, 31) + (this.fu_1 == null ? 0 : hashCode(this.fu_1)) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationDisableCommand))
      return false;
    if (!(this.bu_1 === other.bu_1))
      return false;
    if (!(this.cu_1 == other.cu_1))
      return false;
    if (!(this.du_1 === other.du_1))
      return false;
    if (!equals(this.eu_1, other.eu_1))
      return false;
    if (!equals(this.fu_1, other.fu_1))
      return false;
    return true;
  }
  static hu(seen0, id, disabledBy, anonymize, attributes, userAttributes, serializationConstructorMarker) {
    Companion_getInstance_50();
    if (!(31 === (31 & seen0))) {
      throwMissingFieldException(seen0, 31, $serializer_getInstance_31().au_1);
    }
    var $this = createThis(this);
    $this.bu_1 = id;
    $this.cu_1 = disabledBy;
    $this.du_1 = anonymize;
    $this.eu_1 = attributes;
    $this.fu_1 = userAttributes;
    return $this;
  }
  get id() {
    return this.ri();
  }
  get disabledBy() {
    return this.wt();
  }
  get anonymize() {
    return this.xt();
  }
  get attributes() {
    return this.at();
  }
  get userAttributes() {
    return this.yt();
  }
}
class OrganizationDisabledEventDTO {}
class Companion_51 {
  constructor() {
    Companion_instance_51 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.ju_1 = [null, lazy(tmp_0, OrganizationDisabledEvent$Companion$$childSerializers$_anonymous__ds5xzz)];
  }
}
class $serializer_32 {
  constructor() {
    $serializer_instance_32 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.command.OrganizationDisabledEvent', this, 2);
    tmp0_serialDesc.ki('id', false);
    tmp0_serialDesc.ki('userIds', false);
    this.ku_1 = tmp0_serialDesc;
  }
  ve() {
    return this.ku_1;
  }
  hi() {
    var tmp0_cached = Companion_getInstance_51().ju_1;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), tmp0_cached[1].d1()];
  }
}
class OrganizationDisabledEvent {
  constructor(id, userIds) {
    Companion_getInstance_51();
    this.lu_1 = id;
    this.mu_1 = userIds;
  }
  ri() {
    return this.lu_1;
  }
  iu() {
    return this.mu_1;
  }
  dd() {
    return this.lu_1;
  }
  ed() {
    return this.mu_1;
  }
  nu(id, userIds) {
    return new OrganizationDisabledEvent(id, userIds);
  }
  copy(id, userIds, $super) {
    id = id === VOID ? this.lu_1 : id;
    userIds = userIds === VOID ? this.mu_1 : userIds;
    return $super === VOID ? this.nu(id, userIds) : $super.nu.call(this, id, userIds);
  }
  toString() {
    return 'OrganizationDisabledEvent(id=' + this.lu_1 + ', userIds=' + toString_1(this.mu_1) + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.lu_1);
    result = imul_0(result, 31) + hashCode(this.mu_1) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationDisabledEvent))
      return false;
    if (!(this.lu_1 === other.lu_1))
      return false;
    if (!equals(this.mu_1, other.mu_1))
      return false;
    return true;
  }
  static ou(seen0, id, userIds, serializationConstructorMarker) {
    Companion_getInstance_51();
    if (!(3 === (3 & seen0))) {
      throwMissingFieldException(seen0, 3, $serializer_getInstance_32().ku_1);
    }
    var $this = createThis(this);
    $this.lu_1 = id;
    $this.mu_1 = userIds;
    return $this;
  }
  get id() {
    return this.ri();
  }
  get userIds() {
    return this.iu();
  }
}
class OrganizationUpdateCommandDTO {}
class Companion_52 {
  constructor() {
    Companion_instance_52 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    var tmp_1 = lazy(tmp_0, OrganizationUpdateCommand$Companion$$childSerializers$_anonymous__c5p751);
    var tmp_2 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.pu_1 = [null, null, null, null, null, tmp_1, lazy(tmp_2, OrganizationUpdateCommand$Companion$$childSerializers$_anonymous__c5p751_0), null];
  }
}
class $serializer_33 {
  constructor() {
    $serializer_instance_33 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.command.OrganizationUpdateCommand', this, 8);
    tmp0_serialDesc.ki('id', false);
    tmp0_serialDesc.ki('name', false);
    tmp0_serialDesc.ki('description', false);
    tmp0_serialDesc.ki('address', false);
    tmp0_serialDesc.ki('website', false);
    tmp0_serialDesc.ki('roles', false);
    tmp0_serialDesc.ki('attributes', false);
    tmp0_serialDesc.ki('status', false);
    this.qu_1 = tmp0_serialDesc;
  }
  ve() {
    return this.qu_1;
  }
  hi() {
    var tmp0_cached = Companion_getInstance_52().pu_1;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), StringSerializer_getInstance(), get_nullable(StringSerializer_getInstance()), get_nullable($serializer_getInstance_5()), get_nullable(StringSerializer_getInstance()), get_nullable(tmp0_cached[5].d1()), get_nullable(tmp0_cached[6].d1()), get_nullable(StringSerializer_getInstance())];
  }
}
class OrganizationUpdateCommand {
  constructor(id, name, description, address, website, roles, attributes, status) {
    Companion_getInstance_52();
    this.ru_1 = id;
    this.su_1 = name;
    this.tu_1 = description;
    this.uu_1 = address;
    this.vu_1 = website;
    this.wu_1 = roles;
    this.xu_1 = attributes;
    this.yu_1 = status;
  }
  ri() {
    return this.ru_1;
  }
  n1() {
    return this.su_1;
  }
  gm() {
    return this.tu_1;
  }
  xs() {
    return this.uu_1;
  }
  ys() {
    return this.vu_1;
  }
  sl() {
    return this.wu_1;
  }
  at() {
    return this.xu_1;
  }
  bt() {
    return this.yu_1;
  }
  dd() {
    return this.ru_1;
  }
  ed() {
    return this.su_1;
  }
  dm() {
    return this.tu_1;
  }
  um() {
    return this.uu_1;
  }
  eo() {
    return this.vu_1;
  }
  hp() {
    return this.wu_1;
  }
  xp() {
    return this.xu_1;
  }
  yp() {
    return this.yu_1;
  }
  zu(id, name, description, address, website, roles, attributes, status) {
    return new OrganizationUpdateCommand(id, name, description, address, website, roles, attributes, status);
  }
  copy(id, name, description, address, website, roles, attributes, status, $super) {
    id = id === VOID ? this.ru_1 : id;
    name = name === VOID ? this.su_1 : name;
    description = description === VOID ? this.tu_1 : description;
    address = address === VOID ? this.uu_1 : address;
    website = website === VOID ? this.vu_1 : website;
    roles = roles === VOID ? this.wu_1 : roles;
    attributes = attributes === VOID ? this.xu_1 : attributes;
    status = status === VOID ? this.yu_1 : status;
    return $super === VOID ? this.zu(id, name, description, address, website, roles, attributes, status) : $super.zu.call(this, id, name, description, address, website, roles, attributes, status);
  }
  toString() {
    return 'OrganizationUpdateCommand(id=' + this.ru_1 + ', name=' + this.su_1 + ', description=' + this.tu_1 + ', address=' + toString_0(this.uu_1) + ', website=' + this.vu_1 + ', roles=' + toString_0(this.wu_1) + ', attributes=' + toString_0(this.xu_1) + ', status=' + this.yu_1 + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.ru_1);
    result = imul_0(result, 31) + getStringHashCode(this.su_1) | 0;
    result = imul_0(result, 31) + (this.tu_1 == null ? 0 : getStringHashCode(this.tu_1)) | 0;
    result = imul_0(result, 31) + (this.uu_1 == null ? 0 : this.uu_1.hashCode()) | 0;
    result = imul_0(result, 31) + (this.vu_1 == null ? 0 : getStringHashCode(this.vu_1)) | 0;
    result = imul_0(result, 31) + (this.wu_1 == null ? 0 : hashCode(this.wu_1)) | 0;
    result = imul_0(result, 31) + (this.xu_1 == null ? 0 : hashCode(this.xu_1)) | 0;
    result = imul_0(result, 31) + (this.yu_1 == null ? 0 : getStringHashCode(this.yu_1)) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationUpdateCommand))
      return false;
    if (!(this.ru_1 === other.ru_1))
      return false;
    if (!(this.su_1 === other.su_1))
      return false;
    if (!(this.tu_1 == other.tu_1))
      return false;
    if (!equals(this.uu_1, other.uu_1))
      return false;
    if (!(this.vu_1 == other.vu_1))
      return false;
    if (!equals(this.wu_1, other.wu_1))
      return false;
    if (!equals(this.xu_1, other.xu_1))
      return false;
    if (!(this.yu_1 == other.yu_1))
      return false;
    return true;
  }
  static av(seen0, id, name, description, address, website, roles, attributes, status, serializationConstructorMarker) {
    Companion_getInstance_52();
    if (!(255 === (255 & seen0))) {
      throwMissingFieldException(seen0, 255, $serializer_getInstance_33().qu_1);
    }
    var $this = createThis(this);
    $this.ru_1 = id;
    $this.su_1 = name;
    $this.tu_1 = description;
    $this.uu_1 = address;
    $this.vu_1 = website;
    $this.wu_1 = roles;
    $this.xu_1 = attributes;
    $this.yu_1 = status;
    return $this;
  }
  get id() {
    return this.ri();
  }
  get name() {
    return this.n1();
  }
  get description() {
    return this.gm();
  }
  get address() {
    return this.xs();
  }
  get website() {
    return this.ys();
  }
  get roles() {
    return this.sl();
  }
  get attributes() {
    return this.at();
  }
  get status() {
    return this.bt();
  }
}
class OrganizationUpdatedResultDTO {}
class Companion_53 {}
class $serializer_34 {
  constructor() {
    $serializer_instance_34 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.command.OrganizationUpdatedResult', this, 1);
    tmp0_serialDesc.ki('id', false);
    this.bv_1 = tmp0_serialDesc;
  }
  ve() {
    return this.bv_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance()];
  }
}
class OrganizationUpdatedResult {
  constructor(id) {
    this.cv_1 = id;
  }
  ri() {
    return this.cv_1;
  }
  dd() {
    return this.cv_1;
  }
  ll(id) {
    return new OrganizationUpdatedResult(id);
  }
  copy(id, $super) {
    id = id === VOID ? this.cv_1 : id;
    return $super === VOID ? this.ll(id) : $super.ll.call(this, id);
  }
  toString() {
    return 'OrganizationUpdatedResult(id=' + this.cv_1 + ')';
  }
  hashCode() {
    return getStringHashCode(this.cv_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationUpdatedResult))
      return false;
    if (!(this.cv_1 === other.cv_1))
      return false;
    return true;
  }
  static dv(seen0, id, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_34().bv_1);
    }
    var $this = createThis(this);
    $this.cv_1 = id;
    return $this;
  }
  get id() {
    return this.ri();
  }
}
class OrganizationUploadLogoCommandDTO {}
class OrganizationUploadedLogoEventDTO {}
class OrganizationDTO {}
class Companion_54 {
  constructor() {
    Companion_instance_54 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    var tmp_1 = lazy(tmp_0, Organization$Companion$$childSerializers$_anonymous__5lsoon);
    var tmp_2 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.jv_1 = [null, null, null, null, null, null, tmp_1, lazy(tmp_2, Organization$Companion$$childSerializers$_anonymous__5lsoon_0), null, null, null, null, null, null];
  }
}
class $serializer_35 {
  constructor() {
    $serializer_instance_35 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.model.Organization', this, 14);
    tmp0_serialDesc.ki('id', false);
    tmp0_serialDesc.ki('siret', false);
    tmp0_serialDesc.ki('name', false);
    tmp0_serialDesc.ki('description', false);
    tmp0_serialDesc.ki('address', false);
    tmp0_serialDesc.ki('website', false);
    tmp0_serialDesc.ki('attributes', false);
    tmp0_serialDesc.ki('roles', false);
    tmp0_serialDesc.ki('logo', false);
    tmp0_serialDesc.ki('status', false);
    tmp0_serialDesc.ki('enabled', false);
    tmp0_serialDesc.ki('disabledBy', false);
    tmp0_serialDesc.ki('creationDate', false);
    tmp0_serialDesc.ki('disabledDate', false);
    this.kv_1 = tmp0_serialDesc;
  }
  ve() {
    return this.kv_1;
  }
  hi() {
    var tmp0_cached = Companion_getInstance_54().jv_1;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), get_nullable(StringSerializer_getInstance()), StringSerializer_getInstance(), get_nullable(StringSerializer_getInstance()), get_nullable($serializer_getInstance_5()), get_nullable(StringSerializer_getInstance()), tmp0_cached[6].d1(), tmp0_cached[7].d1(), get_nullable(StringSerializer_getInstance()), StringSerializer_getInstance(), BooleanSerializer_getInstance(), get_nullable(StringSerializer_getInstance()), LongSerializer_getInstance(), get_nullable(LongSerializer_getInstance())];
  }
}
class Organization {
  constructor(id, siret, name, description, address, website, attributes, roles, logo, status, enabled, disabledBy, creationDate, disabledDate) {
    Companion_getInstance_54();
    this.lv_1 = id;
    this.mv_1 = siret;
    this.nv_1 = name;
    this.ov_1 = description;
    this.pv_1 = address;
    this.qv_1 = website;
    this.rv_1 = attributes;
    this.sv_1 = roles;
    this.tv_1 = logo;
    this.uv_1 = status;
    this.vv_1 = enabled;
    this.wv_1 = disabledBy;
    this.xv_1 = creationDate;
    this.yv_1 = disabledDate;
  }
  ri() {
    return this.lv_1;
  }
  ws() {
    return this.mv_1;
  }
  n1() {
    return this.nv_1;
  }
  gm() {
    return this.ov_1;
  }
  xs() {
    return this.pv_1;
  }
  ys() {
    return this.qv_1;
  }
  at() {
    return this.rv_1;
  }
  sl() {
    return this.sv_1;
  }
  fv() {
    return this.tv_1;
  }
  bt() {
    return this.uv_1;
  }
  gv() {
    return this.vv_1;
  }
  wt() {
    return this.wv_1;
  }
  hv() {
    return this.xv_1;
  }
  iv() {
    return this.yv_1;
  }
  dd() {
    return this.lv_1;
  }
  ed() {
    return this.mv_1;
  }
  dm() {
    return this.nv_1;
  }
  um() {
    return this.ov_1;
  }
  eo() {
    return this.pv_1;
  }
  hp() {
    return this.qv_1;
  }
  xp() {
    return this.rv_1;
  }
  yp() {
    return this.sv_1;
  }
  nt() {
    return this.tv_1;
  }
  zv() {
    return this.uv_1;
  }
  aw() {
    return this.vv_1;
  }
  bw() {
    return this.wv_1;
  }
  cw() {
    return this.xv_1;
  }
  dw() {
    return this.yv_1;
  }
  ew(id, siret, name, description, address, website, attributes, roles, logo, status, enabled, disabledBy, creationDate, disabledDate) {
    return new Organization(id, siret, name, description, address, website, attributes, roles, logo, status, enabled, disabledBy, creationDate, disabledDate);
  }
  copy(id, siret, name, description, address, website, attributes, roles, logo, status, enabled, disabledBy, creationDate, disabledDate, $super) {
    id = id === VOID ? this.lv_1 : id;
    siret = siret === VOID ? this.mv_1 : siret;
    name = name === VOID ? this.nv_1 : name;
    description = description === VOID ? this.ov_1 : description;
    address = address === VOID ? this.pv_1 : address;
    website = website === VOID ? this.qv_1 : website;
    attributes = attributes === VOID ? this.rv_1 : attributes;
    roles = roles === VOID ? this.sv_1 : roles;
    logo = logo === VOID ? this.tv_1 : logo;
    status = status === VOID ? this.uv_1 : status;
    enabled = enabled === VOID ? this.vv_1 : enabled;
    disabledBy = disabledBy === VOID ? this.wv_1 : disabledBy;
    creationDate = creationDate === VOID ? this.xv_1 : creationDate;
    disabledDate = disabledDate === VOID ? this.yv_1 : disabledDate;
    return $super === VOID ? this.ew(id, siret, name, description, address, website, attributes, roles, logo, status, enabled, disabledBy, creationDate, disabledDate) : $super.ew.call(this, id, siret, name, description, address, website, attributes, roles, logo, status, enabled, disabledBy, creationDate, disabledDate);
  }
  toString() {
    return 'Organization(id=' + this.lv_1 + ', siret=' + this.mv_1 + ', name=' + this.nv_1 + ', description=' + this.ov_1 + ', address=' + toString_0(this.pv_1) + ', website=' + this.qv_1 + ', attributes=' + toString_1(this.rv_1) + ', roles=' + toString_1(this.sv_1) + ', logo=' + this.tv_1 + ', status=' + this.uv_1 + ', enabled=' + this.vv_1 + ', disabledBy=' + this.wv_1 + ', creationDate=' + this.xv_1.toString() + ', disabledDate=' + toString_0(this.yv_1) + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.lv_1);
    result = imul_0(result, 31) + (this.mv_1 == null ? 0 : getStringHashCode(this.mv_1)) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.nv_1) | 0;
    result = imul_0(result, 31) + (this.ov_1 == null ? 0 : getStringHashCode(this.ov_1)) | 0;
    result = imul_0(result, 31) + (this.pv_1 == null ? 0 : this.pv_1.hashCode()) | 0;
    result = imul_0(result, 31) + (this.qv_1 == null ? 0 : getStringHashCode(this.qv_1)) | 0;
    result = imul_0(result, 31) + hashCode(this.rv_1) | 0;
    result = imul_0(result, 31) + hashCode(this.sv_1) | 0;
    result = imul_0(result, 31) + (this.tv_1 == null ? 0 : getStringHashCode(this.tv_1)) | 0;
    result = imul_0(result, 31) + getStringHashCode(this.uv_1) | 0;
    result = imul_0(result, 31) + getBooleanHashCode(this.vv_1) | 0;
    result = imul_0(result, 31) + (this.wv_1 == null ? 0 : getStringHashCode(this.wv_1)) | 0;
    result = imul_0(result, 31) + getBigIntHashCode(this.xv_1) | 0;
    result = imul_0(result, 31) + (this.yv_1 == null ? 0 : getBigIntHashCode(this.yv_1)) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof Organization))
      return false;
    if (!(this.lv_1 === other.lv_1))
      return false;
    if (!(this.mv_1 == other.mv_1))
      return false;
    if (!(this.nv_1 === other.nv_1))
      return false;
    if (!(this.ov_1 == other.ov_1))
      return false;
    if (!equals(this.pv_1, other.pv_1))
      return false;
    if (!(this.qv_1 == other.qv_1))
      return false;
    if (!equals(this.rv_1, other.rv_1))
      return false;
    if (!equals(this.sv_1, other.sv_1))
      return false;
    if (!(this.tv_1 == other.tv_1))
      return false;
    if (!(this.uv_1 === other.uv_1))
      return false;
    if (!(this.vv_1 === other.vv_1))
      return false;
    if (!(this.wv_1 == other.wv_1))
      return false;
    if (!(this.xv_1 === other.xv_1))
      return false;
    if (!(this.yv_1 == other.yv_1))
      return false;
    return true;
  }
  static fw(seen0, id, siret, name, description, address, website, attributes, roles, logo, status, enabled, disabledBy, creationDate, disabledDate, serializationConstructorMarker) {
    Companion_getInstance_54();
    if (!(16383 === (16383 & seen0))) {
      throwMissingFieldException(seen0, 16383, $serializer_getInstance_35().kv_1);
    }
    var $this = createThis(this);
    $this.lv_1 = id;
    $this.mv_1 = siret;
    $this.nv_1 = name;
    $this.ov_1 = description;
    $this.pv_1 = address;
    $this.qv_1 = website;
    $this.rv_1 = attributes;
    $this.sv_1 = roles;
    $this.tv_1 = logo;
    $this.uv_1 = status;
    $this.vv_1 = enabled;
    $this.wv_1 = disabledBy;
    $this.xv_1 = creationDate;
    $this.yv_1 = disabledDate;
    return $this;
  }
  get id() {
    return this.ri();
  }
  get siret() {
    return this.ws();
  }
  get name() {
    return this.n1();
  }
  get description() {
    return this.gm();
  }
  get address() {
    return this.xs();
  }
  get website() {
    return this.ys();
  }
  get attributes() {
    return this.at();
  }
  get roles() {
    return this.sl();
  }
  get logo() {
    return this.fv();
  }
  get status() {
    return this.bt();
  }
  get enabled() {
    return this.gv();
  }
  get disabledBy() {
    return this.wt();
  }
  get creationDate() {
    return this.hv();
  }
  get disabledDate() {
    return this.iv();
  }
}
class OrganizationRefDTO {}
class Companion_55 {
  constructor() {
    Companion_instance_55 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.gw_1 = [null, null, lazy(tmp_0, OrganizationRef$Companion$$childSerializers$_anonymous__wrc7pm)];
  }
}
class $serializer_36 {
  constructor() {
    $serializer_instance_36 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.model.OrganizationRef', this, 3);
    tmp0_serialDesc.ki('id', false);
    tmp0_serialDesc.ki('name', false);
    tmp0_serialDesc.ki('roles', false);
    this.hw_1 = tmp0_serialDesc;
  }
  ve() {
    return this.hw_1;
  }
  hi() {
    var tmp0_cached = Companion_getInstance_55().gw_1;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance(), StringSerializer_getInstance(), tmp0_cached[2].d1()];
  }
}
class OrganizationRef {
  constructor(id, name, roles) {
    Companion_getInstance_55();
    this.iw_1 = id;
    this.jw_1 = name;
    this.kw_1 = roles;
  }
  ri() {
    return this.iw_1;
  }
  n1() {
    return this.jw_1;
  }
  sl() {
    return this.kw_1;
  }
  dd() {
    return this.iw_1;
  }
  ed() {
    return this.jw_1;
  }
  dm() {
    return this.kw_1;
  }
  lw(id, name, roles) {
    return new OrganizationRef(id, name, roles);
  }
  copy(id, name, roles, $super) {
    id = id === VOID ? this.iw_1 : id;
    name = name === VOID ? this.jw_1 : name;
    roles = roles === VOID ? this.kw_1 : roles;
    return $super === VOID ? this.lw(id, name, roles) : $super.lw.call(this, id, name, roles);
  }
  toString() {
    return 'OrganizationRef(id=' + this.iw_1 + ', name=' + this.jw_1 + ', roles=' + toString_1(this.kw_1) + ')';
  }
  hashCode() {
    var result = getStringHashCode(this.iw_1);
    result = imul_0(result, 31) + getStringHashCode(this.jw_1) | 0;
    result = imul_0(result, 31) + hashCode(this.kw_1) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationRef))
      return false;
    if (!(this.iw_1 === other.iw_1))
      return false;
    if (!(this.jw_1 === other.jw_1))
      return false;
    if (!equals(this.kw_1, other.kw_1))
      return false;
    return true;
  }
  static mw(seen0, id, name, roles, serializationConstructorMarker) {
    Companion_getInstance_55();
    if (!(7 === (7 & seen0))) {
      throwMissingFieldException(seen0, 7, $serializer_getInstance_36().hw_1);
    }
    var $this = createThis(this);
    $this.iw_1 = id;
    $this.jw_1 = name;
    $this.kw_1 = roles;
    return $this;
  }
  get id() {
    return this.ri();
  }
  get name() {
    return this.n1();
  }
  get roles() {
    return this.sl();
  }
}
class OrganizationStatusValues {
  pending() {
    return 'PENDING';
  }
  validated() {
    return 'VALIDATED';
  }
  rejected() {
    return 'REJECTED';
  }
}
class OrganizationPolicies {
  canGet(authedUser, organizationId) {
    return hasRole(authedUser, ImPermission_IM_ORGANIZATION_READ_getInstance()) || (hasRole(authedUser, ImPermission_IM_MY_ORGANIZATION_READ_getInstance()) && authedUser.memberOf === organizationId);
  }
  canList(authedUser) {
    return hasRole(authedUser, ImPermission_IM_ORGANIZATION_READ_getInstance());
  }
  checkRefList(authedUser) {
    return !(authedUser == null) && hasOneOfRoles(authedUser, [ImPermission_IM_ORGANIZATION_READ_getInstance(), ImPermission_IM_MY_ORGANIZATION_READ_getInstance()]);
  }
  canCreate(authedUser) {
    return hasRole(authedUser, ImPermission_IM_ORGANIZATION_WRITE_getInstance());
  }
  canUpdate(authedUser, organizationId) {
    return hasRole(authedUser, ImPermission_IM_ORGANIZATION_WRITE_getInstance()) || (hasRole(authedUser, ImPermission_IM_MY_ORGANIZATION_WRITE_getInstance()) && authedUser.memberOf === organizationId);
  }
  canUpdateStatus(authedUser) {
    return hasRole(authedUser, ImPermission_IM_ORGANIZATION_STATUS_WRITE_getInstance());
  }
  canDisable(authedUser) {
    return hasRole(authedUser, ImPermission_IM_ORGANIZATION_WRITE_getInstance());
  }
  canDelete(authedUser) {
    return hasRole(authedUser, ImPermission_IM_ORGANIZATION_WRITE_getInstance());
  }
}
class OrganizationGetFromInseeQueryDTO {}
class Companion_56 {}
class $serializer_37 {
  constructor() {
    $serializer_instance_37 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.query.OrganizationGetFromInseeQuery', this, 1);
    tmp0_serialDesc.ki('siret', false);
    this.nw_1 = tmp0_serialDesc;
  }
  ve() {
    return this.nw_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance()];
  }
}
class OrganizationGetFromInseeQuery {
  constructor(siret) {
    this.ow_1 = siret;
  }
  ws() {
    return this.ow_1;
  }
  dd() {
    return this.ow_1;
  }
  ll(siret) {
    return new OrganizationGetFromInseeQuery(siret);
  }
  copy(siret, $super) {
    siret = siret === VOID ? this.ow_1 : siret;
    return $super === VOID ? this.ll(siret) : $super.ll.call(this, siret);
  }
  toString() {
    return 'OrganizationGetFromInseeQuery(siret=' + this.ow_1 + ')';
  }
  hashCode() {
    return getStringHashCode(this.ow_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationGetFromInseeQuery))
      return false;
    if (!(this.ow_1 === other.ow_1))
      return false;
    return true;
  }
  static pw(seen0, siret, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_37().nw_1);
    }
    var $this = createThis(this);
    $this.ow_1 = siret;
    return $this;
  }
  get siret() {
    return this.ws();
  }
}
class OrganizationGetFromInseeResultDTO {}
class Companion_57 {}
class $serializer_38 {
  constructor() {
    $serializer_instance_38 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.query.OrganizationGetFromInseeResult', this, 1);
    tmp0_serialDesc.ki('item', false);
    this.qw_1 = tmp0_serialDesc;
  }
  ve() {
    return this.qw_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [get_nullable($serializer_getInstance_35())];
  }
}
class OrganizationGetFromInseeResult {
  constructor(item) {
    this.rw_1 = item;
  }
  an() {
    return this.rw_1;
  }
  dd() {
    return this.rw_1;
  }
  sw(item) {
    return new OrganizationGetFromInseeResult(item);
  }
  copy(item, $super) {
    item = item === VOID ? this.rw_1 : item;
    return $super === VOID ? this.sw(item) : $super.sw.call(this, item);
  }
  toString() {
    return 'OrganizationGetFromInseeResult(item=' + toString_0(this.rw_1) + ')';
  }
  hashCode() {
    return this.rw_1 == null ? 0 : this.rw_1.hashCode();
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationGetFromInseeResult))
      return false;
    if (!equals(this.rw_1, other.rw_1))
      return false;
    return true;
  }
  static tw(seen0, item, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_38().qw_1);
    }
    var $this = createThis(this);
    $this.rw_1 = item;
    return $this;
  }
  get item() {
    return this.an();
  }
}
class OrganizationGetQueryDTO {}
class Companion_58 {}
class $serializer_39 {
  constructor() {
    $serializer_instance_39 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.query.OrganizationGetQuery', this, 1);
    tmp0_serialDesc.ki('id', false);
    this.uw_1 = tmp0_serialDesc;
  }
  ve() {
    return this.uw_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance()];
  }
}
class OrganizationGetQuery {
  constructor(id) {
    this.vw_1 = id;
  }
  ri() {
    return this.vw_1;
  }
  dd() {
    return this.vw_1;
  }
  ll(id) {
    return new OrganizationGetQuery(id);
  }
  copy(id, $super) {
    id = id === VOID ? this.vw_1 : id;
    return $super === VOID ? this.ll(id) : $super.ll.call(this, id);
  }
  toString() {
    return 'OrganizationGetQuery(id=' + this.vw_1 + ')';
  }
  hashCode() {
    return getStringHashCode(this.vw_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationGetQuery))
      return false;
    if (!(this.vw_1 === other.vw_1))
      return false;
    return true;
  }
  static ww(seen0, id, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_39().uw_1);
    }
    var $this = createThis(this);
    $this.vw_1 = id;
    return $this;
  }
  get id() {
    return this.ri();
  }
}
class OrganizationGetResultDTO {}
class Companion_59 {}
class $serializer_40 {
  constructor() {
    $serializer_instance_40 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.query.OrganizationGetResult', this, 1);
    tmp0_serialDesc.ki('item', false);
    this.xw_1 = tmp0_serialDesc;
  }
  ve() {
    return this.xw_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [get_nullable($serializer_getInstance_35())];
  }
}
class OrganizationGetResult {
  constructor(item) {
    this.yw_1 = item;
  }
  an() {
    return this.yw_1;
  }
  dd() {
    return this.yw_1;
  }
  sw(item) {
    return new OrganizationGetResult(item);
  }
  copy(item, $super) {
    item = item === VOID ? this.yw_1 : item;
    return $super === VOID ? this.sw(item) : $super.sw.call(this, item);
  }
  toString() {
    return 'OrganizationGetResult(item=' + toString_0(this.yw_1) + ')';
  }
  hashCode() {
    return this.yw_1 == null ? 0 : this.yw_1.hashCode();
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationGetResult))
      return false;
    if (!equals(this.yw_1, other.yw_1))
      return false;
    return true;
  }
  static zw(seen0, item, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_40().xw_1);
    }
    var $this = createThis(this);
    $this.yw_1 = item;
    return $this;
  }
  get item() {
    return this.an();
  }
}
class OrganizationPageQueryDTO {}
class Companion_60 {
  constructor() {
    Companion_instance_60 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    var tmp_1 = lazy(tmp_0, OrganizationPageQuery$Companion$$childSerializers$_anonymous__umqvvo);
    var tmp_2 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    var tmp_3 = lazy(tmp_2, OrganizationPageQuery$Companion$$childSerializers$_anonymous__umqvvo_0);
    var tmp_4 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.cx_1 = [null, null, tmp_1, tmp_3, null, null, null, lazy(tmp_4, OrganizationPageQuery$Companion$$childSerializers$_anonymous__umqvvo_1)];
  }
}
class $serializer_41 {
  constructor() {
    $serializer_instance_41 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.query.OrganizationPageQuery', this, 8);
    tmp0_serialDesc.ki('name', true);
    tmp0_serialDesc.ki('role', true);
    tmp0_serialDesc.ki('attributes', true);
    tmp0_serialDesc.ki('status', true);
    tmp0_serialDesc.ki('withDisabled', true);
    tmp0_serialDesc.ki('offset', false);
    tmp0_serialDesc.ki('limit', false);
    tmp0_serialDesc.ki('roles', true);
    this.dx_1 = tmp0_serialDesc;
  }
  ve() {
    return this.dx_1;
  }
  hi() {
    var tmp0_cached = Companion_getInstance_60().cx_1;
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [get_nullable(StringSerializer_getInstance()), get_nullable(StringSerializer_getInstance()), get_nullable(tmp0_cached[2].d1()), get_nullable(tmp0_cached[3].d1()), get_nullable(BooleanSerializer_getInstance()), get_nullable(IntSerializer_getInstance()), get_nullable(IntSerializer_getInstance()), get_nullable(tmp0_cached[7].d1())];
  }
}
class OrganizationPageQuery {
  constructor(name, role, attributes, status, withDisabled, offset, limit, roles) {
    Companion_getInstance_60();
    name = name === VOID ? null : name;
    role = role === VOID ? null : role;
    attributes = attributes === VOID ? null : attributes;
    status = status === VOID ? null : status;
    withDisabled = withDisabled === VOID ? false : withDisabled;
    roles = roles === VOID ? null : roles;
    this.ex_1 = name;
    this.fx_1 = role;
    this.gx_1 = attributes;
    this.hx_1 = status;
    this.ix_1 = withDisabled;
    this.jx_1 = offset;
    this.kx_1 = limit;
    this.lx_1 = roles;
  }
  n1() {
    return this.ex_1;
  }
  ax() {
    return this.fx_1;
  }
  at() {
    return this.gx_1;
  }
  bt() {
    return this.hx_1;
  }
  bx() {
    return this.ix_1;
  }
  yk() {
    return this.jx_1;
  }
  zk() {
    return this.kx_1;
  }
  sl() {
    return this.lx_1;
  }
  dd() {
    return this.ex_1;
  }
  ed() {
    return this.fx_1;
  }
  dm() {
    return this.gx_1;
  }
  um() {
    return this.hx_1;
  }
  eo() {
    return this.ix_1;
  }
  hp() {
    return this.jx_1;
  }
  xp() {
    return this.kx_1;
  }
  yp() {
    return this.lx_1;
  }
  mx(name, role, attributes, status, withDisabled, offset, limit, roles) {
    return new OrganizationPageQuery(name, role, attributes, status, withDisabled, offset, limit, roles);
  }
  copy(name, role, attributes, status, withDisabled, offset, limit, roles, $super) {
    name = name === VOID ? this.ex_1 : name;
    role = role === VOID ? this.fx_1 : role;
    attributes = attributes === VOID ? this.gx_1 : attributes;
    status = status === VOID ? this.hx_1 : status;
    withDisabled = withDisabled === VOID ? this.ix_1 : withDisabled;
    offset = offset === VOID ? this.jx_1 : offset;
    limit = limit === VOID ? this.kx_1 : limit;
    roles = roles === VOID ? this.lx_1 : roles;
    return $super === VOID ? this.mx(name, role, attributes, status, withDisabled, offset, limit, roles) : $super.mx.call(this, name, role, attributes, status, withDisabled, offset, limit, roles);
  }
  toString() {
    return 'OrganizationPageQuery(name=' + this.ex_1 + ', role=' + this.fx_1 + ', attributes=' + toString_0(this.gx_1) + ', status=' + toString_0(this.hx_1) + ', withDisabled=' + this.ix_1 + ', offset=' + this.jx_1 + ', limit=' + this.kx_1 + ', roles=' + toString_0(this.lx_1) + ')';
  }
  hashCode() {
    var result = this.ex_1 == null ? 0 : getStringHashCode(this.ex_1);
    result = imul_0(result, 31) + (this.fx_1 == null ? 0 : getStringHashCode(this.fx_1)) | 0;
    result = imul_0(result, 31) + (this.gx_1 == null ? 0 : hashCode(this.gx_1)) | 0;
    result = imul_0(result, 31) + (this.hx_1 == null ? 0 : hashCode(this.hx_1)) | 0;
    result = imul_0(result, 31) + (this.ix_1 == null ? 0 : getBooleanHashCode(this.ix_1)) | 0;
    result = imul_0(result, 31) + (this.jx_1 == null ? 0 : this.jx_1) | 0;
    result = imul_0(result, 31) + (this.kx_1 == null ? 0 : this.kx_1) | 0;
    result = imul_0(result, 31) + (this.lx_1 == null ? 0 : hashCode(this.lx_1)) | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationPageQuery))
      return false;
    if (!(this.ex_1 == other.ex_1))
      return false;
    if (!(this.fx_1 == other.fx_1))
      return false;
    if (!equals(this.gx_1, other.gx_1))
      return false;
    if (!equals(this.hx_1, other.hx_1))
      return false;
    if (!(this.ix_1 == other.ix_1))
      return false;
    if (!(this.jx_1 == other.jx_1))
      return false;
    if (!(this.kx_1 == other.kx_1))
      return false;
    if (!equals(this.lx_1, other.lx_1))
      return false;
    return true;
  }
  static nx(seen0, name, role, attributes, status, withDisabled, offset, limit, roles, serializationConstructorMarker) {
    Companion_getInstance_60();
    if (!(96 === (96 & seen0))) {
      throwMissingFieldException(seen0, 96, $serializer_getInstance_41().dx_1);
    }
    var $this = createThis(this);
    if (0 === (seen0 & 1))
      $this.ex_1 = null;
    else
      $this.ex_1 = name;
    if (0 === (seen0 & 2))
      $this.fx_1 = null;
    else
      $this.fx_1 = role;
    if (0 === (seen0 & 4))
      $this.gx_1 = null;
    else
      $this.gx_1 = attributes;
    if (0 === (seen0 & 8))
      $this.hx_1 = null;
    else
      $this.hx_1 = status;
    if (0 === (seen0 & 16))
      $this.ix_1 = false;
    else
      $this.ix_1 = withDisabled;
    $this.jx_1 = offset;
    $this.kx_1 = limit;
    if (0 === (seen0 & 128))
      $this.lx_1 = null;
    else
      $this.lx_1 = roles;
    return $this;
  }
  get name() {
    return this.n1();
  }
  get role() {
    return this.ax();
  }
  get attributes() {
    return this.at();
  }
  get status() {
    return this.bt();
  }
  get withDisabled() {
    return this.bx();
  }
  get offset() {
    return this.yk();
  }
  get limit() {
    return this.zk();
  }
  get roles() {
    return this.sl();
  }
}
class OrganizationPageResultDTO {}
class Companion_61 {
  constructor() {
    Companion_instance_61 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.ox_1 = [lazy(tmp_0, OrganizationPageResult$Companion$$childSerializers$_anonymous__sz1e6j), null];
  }
}
class $serializer_42 {
  constructor() {
    $serializer_instance_42 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.query.OrganizationPageResult', this, 2);
    tmp0_serialDesc.ki('items', false);
    tmp0_serialDesc.ki('total', false);
    this.px_1 = tmp0_serialDesc;
  }
  ve() {
    return this.px_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [Companion_getInstance_61().ox_1[0].d1(), IntSerializer_getInstance()];
  }
}
class OrganizationPageResult {
  constructor(items, total) {
    Companion_getInstance_61();
    this.qx_1 = items;
    this.rx_1 = total;
  }
  jk() {
    return this.qx_1;
  }
  ik() {
    return this.rx_1;
  }
  dd() {
    return this.qx_1;
  }
  ed() {
    return this.rx_1;
  }
  sx(items, total) {
    return new OrganizationPageResult(items, total);
  }
  copy(items, total, $super) {
    items = items === VOID ? this.qx_1 : items;
    total = total === VOID ? this.rx_1 : total;
    return $super === VOID ? this.sx(items, total) : $super.sx.call(this, items, total);
  }
  toString() {
    return 'OrganizationPageResult(items=' + toString_1(this.qx_1) + ', total=' + this.rx_1 + ')';
  }
  hashCode() {
    var result = hashCode(this.qx_1);
    result = imul_0(result, 31) + this.rx_1 | 0;
    return result;
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationPageResult))
      return false;
    if (!equals(this.qx_1, other.qx_1))
      return false;
    if (!(this.rx_1 === other.rx_1))
      return false;
    return true;
  }
  static tx(seen0, items, total, serializationConstructorMarker) {
    Companion_getInstance_61();
    if (!(3 === (3 & seen0))) {
      throwMissingFieldException(seen0, 3, $serializer_getInstance_42().px_1);
    }
    var $this = createThis(this);
    $this.qx_1 = items;
    $this.rx_1 = total;
    return $this;
  }
  get items() {
    return this.jk();
  }
  get total() {
    return this.ik();
  }
}
class OrganizationRefGetQueryDTO {}
class Companion_62 {}
class $serializer_43 {
  constructor() {
    $serializer_instance_43 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.query.OrganizationRefGetQuery', this, 1);
    tmp0_serialDesc.ki('id', false);
    this.ux_1 = tmp0_serialDesc;
  }
  ve() {
    return this.ux_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [StringSerializer_getInstance()];
  }
}
class OrganizationRefGetQuery {
  constructor(id) {
    this.vx_1 = id;
  }
  ri() {
    return this.vx_1;
  }
  dd() {
    return this.vx_1;
  }
  ll(id) {
    return new OrganizationRefGetQuery(id);
  }
  copy(id, $super) {
    id = id === VOID ? this.vx_1 : id;
    return $super === VOID ? this.ll(id) : $super.ll.call(this, id);
  }
  toString() {
    return 'OrganizationRefGetQuery(id=' + this.vx_1 + ')';
  }
  hashCode() {
    return getStringHashCode(this.vx_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationRefGetQuery))
      return false;
    if (!(this.vx_1 === other.vx_1))
      return false;
    return true;
  }
  static wx(seen0, id, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_43().ux_1);
    }
    var $this = createThis(this);
    $this.vx_1 = id;
    return $this;
  }
  get id() {
    return this.ri();
  }
}
class OrganizationRefGetResultDTO {}
class Companion_63 {}
class $serializer_44 {
  constructor() {
    $serializer_instance_44 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.query.OrganizationRefGetResult', this, 1);
    tmp0_serialDesc.ki('item', false);
    this.xx_1 = tmp0_serialDesc;
  }
  ve() {
    return this.xx_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [get_nullable($serializer_getInstance_36())];
  }
}
class OrganizationRefGetResult {
  constructor(item) {
    this.yx_1 = item;
  }
  an() {
    return this.yx_1;
  }
  dd() {
    return this.yx_1;
  }
  zx(item) {
    return new OrganizationRefGetResult(item);
  }
  copy(item, $super) {
    item = item === VOID ? this.yx_1 : item;
    return $super === VOID ? this.zx(item) : $super.zx.call(this, item);
  }
  toString() {
    return 'OrganizationRefGetResult(item=' + toString_0(this.yx_1) + ')';
  }
  hashCode() {
    return this.yx_1 == null ? 0 : this.yx_1.hashCode();
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationRefGetResult))
      return false;
    if (!equals(this.yx_1, other.yx_1))
      return false;
    return true;
  }
  static ay(seen0, item, serializationConstructorMarker) {
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_44().xx_1);
    }
    var $this = createThis(this);
    $this.yx_1 = item;
    return $this;
  }
  get item() {
    return this.an();
  }
}
class OrganizationRefListQueryDTO {}
class Companion_64 {}
class $serializer_45 {
  constructor() {
    $serializer_instance_45 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.query.OrganizationRefListQuery', this, 1);
    tmp0_serialDesc.ki('withDisabled', true);
    this.by_1 = tmp0_serialDesc;
  }
  ve() {
    return this.by_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [BooleanSerializer_getInstance()];
  }
}
class OrganizationRefListQuery {
  constructor(withDisabled) {
    withDisabled = withDisabled === VOID ? false : withDisabled;
    this.cy_1 = withDisabled;
  }
  bx() {
    return this.cy_1;
  }
  dd() {
    return this.cy_1;
  }
  dy(withDisabled) {
    return new OrganizationRefListQuery(withDisabled);
  }
  copy(withDisabled, $super) {
    withDisabled = withDisabled === VOID ? this.cy_1 : withDisabled;
    return $super === VOID ? this.dy(withDisabled) : $super.dy.call(this, withDisabled);
  }
  toString() {
    return 'OrganizationRefListQuery(withDisabled=' + this.cy_1 + ')';
  }
  hashCode() {
    return getBooleanHashCode(this.cy_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationRefListQuery))
      return false;
    if (!(this.cy_1 === other.cy_1))
      return false;
    return true;
  }
  static ey(seen0, withDisabled, serializationConstructorMarker) {
    if (!(0 === (0 & seen0))) {
      throwMissingFieldException(seen0, 0, $serializer_getInstance_45().by_1);
    }
    var $this = createThis(this);
    if (0 === (seen0 & 1))
      $this.cy_1 = false;
    else
      $this.cy_1 = withDisabled;
    return $this;
  }
  get withDisabled() {
    return this.bx();
  }
}
class OrganizationRefListResultDTO {}
class Companion_65 {
  constructor() {
    Companion_instance_65 = this;
    var tmp = this;
    var tmp_0 = LazyThreadSafetyMode_PUBLICATION_getInstance();
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    tmp.fy_1 = [lazy(tmp_0, OrganizationRefListResult$Companion$$childSerializers$_anonymous__k7o9rl)];
  }
}
class $serializer_46 {
  constructor() {
    $serializer_instance_46 = this;
    var tmp0_serialDesc = new PluginGeneratedSerialDescriptor('io.komune.im.f2.organization.domain.query.OrganizationRefListResult', this, 1);
    tmp0_serialDesc.ki('items', false);
    this.gy_1 = tmp0_serialDesc;
  }
  ve() {
    return this.gy_1;
  }
  hi() {
    // Inline function 'kotlin.arrayOf' call
    // Inline function 'kotlin.js.unsafeCast' call
    // Inline function 'kotlin.js.asDynamic' call
    return [Companion_getInstance_65().fy_1[0].d1()];
  }
}
class OrganizationRefListResult {
  constructor(items) {
    Companion_getInstance_65();
    this.hy_1 = items;
  }
  jk() {
    return this.hy_1;
  }
  dd() {
    return this.hy_1;
  }
  iy(items) {
    return new OrganizationRefListResult(items);
  }
  copy(items, $super) {
    items = items === VOID ? this.hy_1 : items;
    return $super === VOID ? this.iy(items) : $super.iy.call(this, items);
  }
  toString() {
    return 'OrganizationRefListResult(items=' + toString_1(this.hy_1) + ')';
  }
  hashCode() {
    return hashCode(this.hy_1);
  }
  equals(other) {
    if (this === other)
      return true;
    if (!(other instanceof OrganizationRefListResult))
      return false;
    if (!equals(this.hy_1, other.hy_1))
      return false;
    return true;
  }
  static jy(seen0, items, serializationConstructorMarker) {
    Companion_getInstance_65();
    if (!(1 === (1 & seen0))) {
      throwMissingFieldException(seen0, 1, $serializer_getInstance_46().gy_1);
    }
    var $this = createThis(this);
    $this.hy_1 = items;
    return $this;
  }
  get items() {
    return this.jk();
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
function contains(_this__u8e3s4, element) {
  return indexOf(_this__u8e3s4, element) >= 0;
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
    return Companion_getInstance_7().r_1;
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
function Char__minus_impl_a2frrh($this, other) {
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
function fromJsMap(map) {
  return Companion_instance_0.e1(map);
}
var Companion_instance_1;
function Companion_getInstance_1() {
  return Companion_instance_1;
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
function get_ZERO() {
  _init_properties_boxedLong_kt__v24qrw();
  return ZERO;
}
var ZERO;
function get_ONE() {
  _init_properties_boxedLong_kt__v24qrw();
  return ONE;
}
var ONE;
var NEG_ONE;
function get_MAX_VALUE() {
  _init_properties_boxedLong_kt__v24qrw();
  return MAX_VALUE;
}
var MAX_VALUE;
function get_MIN_VALUE() {
  _init_properties_boxedLong_kt__v24qrw();
  return MIN_VALUE;
}
var MIN_VALUE;
function get_TWO_PWR_24_() {
  _init_properties_boxedLong_kt__v24qrw();
  return TWO_PWR_24_;
}
var TWO_PWR_24_;
var longArrayClass;
function compare(_this__u8e3s4, other) {
  _init_properties_boxedLong_kt__v24qrw();
  if (equalsLong(_this__u8e3s4, other)) {
    return 0;
  }
  var thisNeg = isNegative(_this__u8e3s4);
  var otherNeg = isNegative(other);
  return thisNeg && !otherNeg ? -1 : !thisNeg && otherNeg ? 1 : isNegative(subtract(_this__u8e3s4, other)) ? -1 : 1;
}
function toNumber(_this__u8e3s4) {
  _init_properties_boxedLong_kt__v24qrw();
  return highBits(_this__u8e3s4) * 4.294967296E9 + getLowBitsUnsigned(_this__u8e3s4);
}
function toStringImpl(_this__u8e3s4, radix) {
  _init_properties_boxedLong_kt__v24qrw();
  if (isZero(_this__u8e3s4)) {
    return '0';
  }
  if (isNegative(_this__u8e3s4)) {
    if (equalsLong(_this__u8e3s4, get_MIN_VALUE())) {
      var radixLong = fromInt(radix);
      var div = divide(_this__u8e3s4, radixLong);
      var rem = convertToInt(subtract(multiply(div, radixLong), _this__u8e3s4));
      var tmp = toStringImpl(div, radix);
      // Inline function 'kotlin.js.asDynamic' call
      // Inline function 'kotlin.js.unsafeCast' call
      return tmp + rem.toString(radix);
    } else {
      return '-' + toStringImpl(negate(_this__u8e3s4), radix);
    }
  }
  var digitsPerTime = radix === 2 ? 31 : radix <= 10 ? 9 : radix <= 21 ? 7 : radix <= 35 ? 6 : 5;
  var radixToPower = fromNumber(Math.pow(radix, digitsPerTime));
  var rem_0 = _this__u8e3s4;
  var result = '';
  while (true) {
    var remDiv = divide(rem_0, radixToPower);
    var intval = convertToInt(subtract(rem_0, multiply(remDiv, radixToPower)));
    // Inline function 'kotlin.js.asDynamic' call
    // Inline function 'kotlin.js.unsafeCast' call
    var digits = intval.toString(radix);
    rem_0 = remDiv;
    if (isZero(rem_0)) {
      return digits + result;
    } else {
      while (digits.length < digitsPerTime) {
        digits = '0' + digits;
      }
      result = digits + result;
    }
  }
}
function equalsLong(_this__u8e3s4, other) {
  _init_properties_boxedLong_kt__v24qrw();
  return highBits(_this__u8e3s4) === highBits(other) && lowBits(_this__u8e3s4) === lowBits(other);
}
function fromInt(value) {
  _init_properties_boxedLong_kt__v24qrw();
  return longFromTwoInts(value, value < 0 ? -1 : 0);
}
function isNegative(_this__u8e3s4) {
  _init_properties_boxedLong_kt__v24qrw();
  return highBits(_this__u8e3s4) < 0;
}
function subtract(_this__u8e3s4, other) {
  _init_properties_boxedLong_kt__v24qrw();
  return add(_this__u8e3s4, negate_0(other));
}
function getLowBitsUnsigned(_this__u8e3s4) {
  _init_properties_boxedLong_kt__v24qrw();
  return lowBits(_this__u8e3s4) >= 0 ? lowBits(_this__u8e3s4) : 4.294967296E9 + lowBits(_this__u8e3s4);
}
function isZero(_this__u8e3s4) {
  _init_properties_boxedLong_kt__v24qrw();
  return highBits(_this__u8e3s4) === 0 && lowBits(_this__u8e3s4) === 0;
}
function multiply(_this__u8e3s4, other) {
  _init_properties_boxedLong_kt__v24qrw();
  if (isZero(_this__u8e3s4)) {
    return get_ZERO();
  } else if (isZero(other)) {
    return get_ZERO();
  }
  if (equalsLong(_this__u8e3s4, get_MIN_VALUE())) {
    return isOdd(other) ? get_MIN_VALUE() : get_ZERO();
  } else if (equalsLong(other, get_MIN_VALUE())) {
    return isOdd(_this__u8e3s4) ? get_MIN_VALUE() : get_ZERO();
  }
  if (isNegative(_this__u8e3s4)) {
    var tmp;
    if (isNegative(other)) {
      tmp = multiply(negate(_this__u8e3s4), negate(other));
    } else {
      tmp = negate(multiply(negate(_this__u8e3s4), other));
    }
    return tmp;
  } else if (isNegative(other)) {
    return negate(multiply(_this__u8e3s4, negate(other)));
  }
  if (lessThan(_this__u8e3s4, get_TWO_PWR_24_()) && lessThan(other, get_TWO_PWR_24_())) {
    return fromNumber(toNumber(_this__u8e3s4) * toNumber(other));
  }
  var a48 = highBits(_this__u8e3s4) >>> 16 | 0;
  var a32 = highBits(_this__u8e3s4) & 65535;
  var a16 = lowBits(_this__u8e3s4) >>> 16 | 0;
  var a00 = lowBits(_this__u8e3s4) & 65535;
  var b48 = highBits(other) >>> 16 | 0;
  var b32 = highBits(other) & 65535;
  var b16 = lowBits(other) >>> 16 | 0;
  var b00 = lowBits(other) & 65535;
  var c48 = 0;
  var c32 = 0;
  var c16 = 0;
  var c00 = 0;
  c00 = c00 + imul_0(a00, b00) | 0;
  c16 = c16 + (c00 >>> 16 | 0) | 0;
  c00 = c00 & 65535;
  c16 = c16 + imul_0(a16, b00) | 0;
  c32 = c32 + (c16 >>> 16 | 0) | 0;
  c16 = c16 & 65535;
  c16 = c16 + imul_0(a00, b16) | 0;
  c32 = c32 + (c16 >>> 16 | 0) | 0;
  c16 = c16 & 65535;
  c32 = c32 + imul_0(a32, b00) | 0;
  c48 = c48 + (c32 >>> 16 | 0) | 0;
  c32 = c32 & 65535;
  c32 = c32 + imul_0(a16, b16) | 0;
  c48 = c48 + (c32 >>> 16 | 0) | 0;
  c32 = c32 & 65535;
  c32 = c32 + imul_0(a00, b32) | 0;
  c48 = c48 + (c32 >>> 16 | 0) | 0;
  c32 = c32 & 65535;
  c48 = c48 + (((imul_0(a48, b00) + imul_0(a32, b16) | 0) + imul_0(a16, b32) | 0) + imul_0(a00, b48) | 0) | 0;
  c48 = c48 & 65535;
  return longFromTwoInts(c16 << 16 | c00, c48 << 16 | c32);
}
function negate(_this__u8e3s4) {
  _init_properties_boxedLong_kt__v24qrw();
  return add_0(invert(_this__u8e3s4), 1n);
}
function fromNumber(value) {
  _init_properties_boxedLong_kt__v24qrw();
  if (isNaN_0(value)) {
    return get_ZERO();
  } else if (value <= -9.223372036854776E18) {
    return get_MIN_VALUE();
  } else if (value + 1 >= 9.223372036854776E18) {
    return get_MAX_VALUE();
  } else if (value < 0) {
    return negate(fromNumber(-value));
  } else {
    var twoPwr32 = 4.294967296E9;
    // Inline function 'kotlin.js.jsBitwiseOr' call
    var tmp = value % twoPwr32 | 0;
    // Inline function 'kotlin.js.jsBitwiseOr' call
    var tmp$ret$1 = value / twoPwr32 | 0;
    return longFromTwoInts(tmp, tmp$ret$1);
  }
}
function add(_this__u8e3s4, other) {
  _init_properties_boxedLong_kt__v24qrw();
  var a48 = highBits(_this__u8e3s4) >>> 16 | 0;
  var a32 = highBits(_this__u8e3s4) & 65535;
  var a16 = lowBits(_this__u8e3s4) >>> 16 | 0;
  var a00 = lowBits(_this__u8e3s4) & 65535;
  var b48 = highBits(other) >>> 16 | 0;
  var b32 = highBits(other) & 65535;
  var b16 = lowBits(other) >>> 16 | 0;
  var b00 = lowBits(other) & 65535;
  var c48 = 0;
  var c32 = 0;
  var c16 = 0;
  var c00 = 0;
  c00 = c00 + (a00 + b00 | 0) | 0;
  c16 = c16 + (c00 >>> 16 | 0) | 0;
  c00 = c00 & 65535;
  c16 = c16 + (a16 + b16 | 0) | 0;
  c32 = c32 + (c16 >>> 16 | 0) | 0;
  c16 = c16 & 65535;
  c32 = c32 + (a32 + b32 | 0) | 0;
  c48 = c48 + (c32 >>> 16 | 0) | 0;
  c32 = c32 & 65535;
  c48 = c48 + (a48 + b48 | 0) | 0;
  c48 = c48 & 65535;
  return longFromTwoInts(c16 << 16 | c00, c48 << 16 | c32);
}
function isOdd(_this__u8e3s4) {
  _init_properties_boxedLong_kt__v24qrw();
  return (lowBits(_this__u8e3s4) & 1) === 1;
}
function lessThan(_this__u8e3s4, other) {
  _init_properties_boxedLong_kt__v24qrw();
  return compare(_this__u8e3s4, other) < 0;
}
function invert(_this__u8e3s4) {
  _init_properties_boxedLong_kt__v24qrw();
  return longFromTwoInts(~lowBits(_this__u8e3s4), ~highBits(_this__u8e3s4));
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
  return ArrayList.y1(tmp$ret$1).z1();
}
function createJsReadonlyMapViewFrom(map) {
  var tmp = createJsReadonlyMapViewFrom$lambda(map);
  var tmp_0 = createJsReadonlyMapViewFrom$lambda_0(map);
  var tmp_1 = createJsReadonlyMapViewFrom$lambda_1(map);
  // Inline function 'kotlin.js.asDynamic' call
  var tmp_2 = UNSUPPORTED_OPERATION$ref();
  // Inline function 'kotlin.js.asDynamic' call
  var tmp_3 = UNSUPPORTED_OPERATION$ref();
  // Inline function 'kotlin.js.asDynamic' call
  var tmp_4 = UNSUPPORTED_OPERATION$ref();
  var tmp_5 = createJsReadonlyMapViewFrom$lambda_2(map);
  var tmp_6 = createJsReadonlyMapViewFrom$lambda_3(map);
  var tmp_7 = createJsReadonlyMapViewFrom$lambda_4(map);
  return createJsMapViewWith(tmp, tmp_0, tmp_1, tmp_2, tmp_3, tmp_4, tmp_5, tmp_6, tmp_7, createJsReadonlyMapViewFrom$lambda_5);
}
function createMapFrom(map) {
  // Inline function 'kotlin.collections.buildMapInternal' call
  // Inline function 'kotlin.apply' call
  var this_0 = LinkedHashMap.g2();
  forEach(createMapFrom$lambda(this_0), map);
  return this_0.z1();
}
function UNSUPPORTED_OPERATION() {
  throw UnsupportedOperationException.k2();
}
function createJsIteratorFrom(iterator, transform) {
  var tmp;
  if (transform === VOID) {
    tmp = createJsIteratorFrom$lambda;
  } else {
    tmp = transform;
  }
  transform = tmp;
  var iteratorNext = createJsIteratorFrom$lambda_0(iterator);
  var iteratorHasNext = createJsIteratorFrom$lambda_1(iterator);
  var iteratorConstructor = typeof Iterator === 'function' ? Iterator : Object;
  var jsIterator = Object.create(iteratorConstructor.prototype);
  jsIterator.next = function () {
    var result = {done: !iteratorHasNext()};
    if (!result.done)
      result.value = transform(iteratorNext());
    return result;
  };
  jsIterator[Symbol.iterator] = function () {
    return this;
  };
  return jsIterator;
}
function forEach(cb, collection, thisArg) {
  thisArg = thisArg === VOID ? undefined : thisArg;
  var iterator = collection.entries();
  var result = iterator.next();
  while (!result.done) {
    var value = result.value;
    // Inline function 'kotlin.js.asDynamic' call
    cb.call(thisArg, value[1], value[0], collection);
    result = iterator.next();
  }
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
function createJsMapViewWith(mapSize, mapGet, mapContains, mapPut, mapRemove, mapClear, keysIterator, valuesIterator, entriesIterator, forEach) {
  // Inline function 'kotlin.also' call
  var this_0 = objectCreate(protoOf(JsMapView));
  this_0[Symbol.iterator] = entriesIterator;
  defineProp(this_0, 'size', mapSize, VOID, true);
  var mapView = this_0;
  return Object.assign(mapView, {get: mapGet, set: function (key, value) {
    mapPut(key, value);
    return this;
  }, 'delete': mapRemove, clear: mapClear, has: mapContains, keys: keysIterator, values: valuesIterator, entries: entriesIterator, forEach: function (cb, thisArg) {
    forEach(cb, mapView, thisArg);
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
function createJsReadonlyMapViewFrom$lambda($map) {
  return () => $map.i();
}
function createJsReadonlyMapViewFrom$lambda_0($map) {
  return (k) => $map.h1(k);
}
function createJsReadonlyMapViewFrom$lambda_1($map) {
  return (k) => $map.f1(k);
}
function createJsReadonlyMapViewFrom$lambda_2($map) {
  return () => createJsIteratorFrom($map.i1().f());
}
function createJsReadonlyMapViewFrom$lambda_3($map) {
  return () => createJsIteratorFrom($map.j1().f());
}
function createJsReadonlyMapViewFrom$lambda$lambda(it) {
  // Inline function 'kotlin.arrayOf' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return [it.c1(), it.d1()];
}
function createJsReadonlyMapViewFrom$lambda_4($map) {
  return () => {
    var tmp = $map.k1().f();
    return createJsIteratorFrom(tmp, createJsReadonlyMapViewFrom$lambda$lambda);
  };
}
function createJsReadonlyMapViewFrom$lambda_5(callback, map, thisArg) {
  forEach(callback, map, thisArg);
  return Unit_instance;
}
function createMapFrom$lambda($$this$buildMapInternal) {
  return (value, key, _unused_var__etf5q3) => {
    $$this$buildMapInternal.l2(key, value);
    return Unit_instance;
  };
}
function createJsIteratorFrom$lambda(it) {
  return it;
}
function createJsIteratorFrom$lambda_0($iterator) {
  return () => $iterator.h();
}
function createJsIteratorFrom$lambda_1($iterator) {
  return () => $iterator.g();
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
          tmp_0 = doubleCompareTo(a, toNumber_0(b));
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
  return a.q1(b);
}
function identityHashCode(obj) {
  return getObjectHashCode(obj);
}
function objectCreate(proto) {
  proto = proto === VOID ? null : proto;
  return Object.create(proto);
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
  throw IllegalStateException.p2(toString_1(message));
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
function get_continuationSymbol() {
  _init_properties_coroutinesInteropJs_kt__7auttl();
  return continuationSymbol;
}
var continuationSymbol;
function orPromise(continuation, lambda) {
  _init_properties_coroutinesInteropJs_kt__7auttl();
  if (continuation != VOID && continuation[get_continuationSymbol()]) {
    return lambda(continuation);
  } else {
    return promisify(lambda);
  }
}
function *$(value, $completion) {
  if (value.constructor === Promise) {
    return yield* await_0(value, $completion);
  } else {
    return yield* value;
  }
}
var properties_initialized_coroutinesInteropJs_kt_p4nog5;
function _init_properties_coroutinesInteropJs_kt__7auttl() {
  if (!properties_initialized_coroutinesInteropJs_kt_p4nog5) {
    properties_initialized_coroutinesInteropJs_kt_p4nog5 = true;
    // Inline function 'kotlin.js.asDynamic' call
    continuationSymbol = Continuation.Symbol;
  }
}
function THROW_NPE() {
  throw NullPointerException.t2();
}
function THROW_CCE() {
  throw ClassCastException.x2();
}
function THROW_IAE(msg) {
  throw IllegalArgumentException.b3(msg);
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
function negate_0(_this__u8e3s4) {
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
function add_0(_this__u8e3s4, other) {
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
function subtract_0(_this__u8e3s4, other) {
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
function multiply_0(_this__u8e3s4, other) {
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
    tmp = fromNumber_0(value);
  } else {
    tmp = tmp0_elvis_lhs;
  }
  return tmp;
}
function fromNumber_0(value) {
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
function toNumber_0(_this__u8e3s4) {
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
    throw IllegalArgumentException.b3(toString_1(message));
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
    throw IllegalArgumentException.b3(toString_1(message));
  }
  return arrayCopyResize(_this__u8e3s4, newSize, null);
}
function asList(_this__u8e3s4) {
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  return ArrayList.y1(_this__u8e3s4);
}
function contentEquals(_this__u8e3s4, other) {
  return contentEqualsInternal(_this__u8e3s4, other);
}
function contentHashCode(_this__u8e3s4) {
  return contentHashCodeInternal(_this__u8e3s4);
}
function digitToIntImpl(_this__u8e3s4) {
  // Inline function 'kotlin.code' call
  var ch = Char__toInt_impl_vasixd(_this__u8e3s4);
  var index = binarySearchRange(Digit_getInstance().c3_1, ch);
  var diff = ch - Digit_getInstance().c3_1[index] | 0;
  return diff < 10 ? diff : -1;
}
function binarySearchRange(array, needle) {
  var bottom = 0;
  var top = array.length - 1 | 0;
  var middle = -1;
  var value = 0;
  while (bottom <= top) {
    middle = (bottom + top | 0) / 2 | 0;
    value = array[middle];
    if (needle > value)
      bottom = middle + 1 | 0;
    else if (needle === value)
      return middle;
    else
      top = middle - 1 | 0;
  }
  return middle - (needle < value ? 1 : 0) | 0;
}
var Digit_instance;
function Digit_getInstance() {
  if (Digit_instance === VOID)
    new Digit();
  return Digit_instance;
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
  return ArrayList.y1(tmp$ret$0);
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
    throw IllegalArgumentException.b3(toString_1(message));
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
var Companion_instance_2;
function Companion_getInstance_2() {
  if (Companion_instance_2 === VOID)
    new Companion_2();
  return Companion_instance_2;
}
function increaseLength($this, amount) {
  var previous = $this.i();
  // Inline function 'kotlin.js.asDynamic' call
  $this.k_1.length = $this.i() + amount | 0;
  return previous;
}
function rangeCheck($this, index) {
  // Inline function 'kotlin.apply' call
  Companion_instance_4.c4(index, $this.i());
  return index;
}
function insertionRangeCheck($this, index) {
  // Inline function 'kotlin.apply' call
  Companion_instance_4.p3(index, $this.i());
  return index;
}
function init_kotlin_collections_HashMap(_this__u8e3s4) {
  _this__u8e3s4.f5_1 = null;
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
  if (!($this.z6_1.p5_1 === $this.b7_1))
    throw ConcurrentModificationException.y6('The backing map has been modified after this entry was obtained.');
}
function _get_capacity__a9k9f3($this) {
  return $this.i5_1.length;
}
function _get_hashSize__tftcho($this) {
  return $this.l5_1.length;
}
function registerModification($this) {
  $this.p5_1 = $this.p5_1 + 1 | 0;
}
function ensureExtraCapacity($this, n) {
  if (shouldCompact($this, n)) {
    compact($this, true);
  } else {
    ensureCapacity($this, $this.n5_1 + n | 0);
  }
}
function shouldCompact($this, extraCapacity) {
  var spareCapacity = _get_capacity__a9k9f3($this) - $this.n5_1 | 0;
  var gaps = $this.n5_1 - $this.i() | 0;
  return spareCapacity < extraCapacity && (gaps + spareCapacity | 0) >= extraCapacity && gaps >= (_get_capacity__a9k9f3($this) / 4 | 0);
}
function ensureCapacity($this, minCapacity) {
  if (minCapacity < 0)
    throw RuntimeException.e7('too many elements');
  if (minCapacity > _get_capacity__a9k9f3($this)) {
    var newSize = Companion_instance_4.f7(_get_capacity__a9k9f3($this), minCapacity);
    $this.i5_1 = copyOfUninitializedElements($this.i5_1, newSize);
    var tmp = $this;
    var tmp0_safe_receiver = $this.j5_1;
    tmp.j5_1 = tmp0_safe_receiver == null ? null : copyOfUninitializedElements(tmp0_safe_receiver, newSize);
    $this.k5_1 = copyOf($this.k5_1, newSize);
    var newHashSize = computeHashSize(Companion_instance_3, newSize);
    if (newHashSize > _get_hashSize__tftcho($this)) {
      rehash($this, newHashSize);
    }
  }
}
function allocateValuesArray($this) {
  var curValuesArray = $this.j5_1;
  if (!(curValuesArray == null))
    return curValuesArray;
  var newValuesArray = arrayOfUninitializedElements(_get_capacity__a9k9f3($this));
  $this.j5_1 = newValuesArray;
  return newValuesArray;
}
function hash($this, key) {
  return key == null ? 0 : imul_0(hashCode(key), -1640531527) >>> $this.o5_1 | 0;
}
function compact($this, updateHashArray) {
  var i = 0;
  var j = 0;
  var valuesArray = $this.j5_1;
  while (i < $this.n5_1) {
    var hash = $this.k5_1[i];
    if (hash >= 0) {
      $this.i5_1[j] = $this.i5_1[i];
      if (!(valuesArray == null)) {
        valuesArray[j] = valuesArray[i];
      }
      if (updateHashArray) {
        $this.k5_1[j] = hash;
        $this.l5_1[hash] = j + 1 | 0;
      }
      j = j + 1 | 0;
    }
    i = i + 1 | 0;
  }
  resetRange($this.i5_1, j, $this.n5_1);
  if (valuesArray == null)
    null;
  else {
    resetRange(valuesArray, j, $this.n5_1);
  }
  $this.n5_1 = j;
}
function rehash($this, newHashSize) {
  registerModification($this);
  if ($this.n5_1 > $this.q5_1) {
    compact($this, false);
  }
  $this.l5_1 = new Int32Array(newHashSize);
  $this.o5_1 = computeShift(Companion_instance_3, newHashSize);
  var i = 0;
  while (i < $this.n5_1) {
    var _unary__edvuaz = i;
    i = _unary__edvuaz + 1 | 0;
    if (!putRehash($this, _unary__edvuaz)) {
      throw IllegalStateException.p2('This cannot happen with fixed magic multiplier and grow-only hash array. Have object hashCodes changed?');
    }
  }
}
function putRehash($this, i) {
  var hash_0 = hash($this, $this.i5_1[i]);
  var probesLeft = $this.m5_1;
  while (true) {
    var index = $this.l5_1[hash_0];
    if (index === 0) {
      $this.l5_1[hash_0] = i + 1 | 0;
      $this.k5_1[i] = hash_0;
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
  var probesLeft = $this.m5_1;
  while (true) {
    var index = $this.l5_1[hash_0];
    if (index === 0)
      return -1;
    if (equals($this.i5_1[index - 1 | 0], key))
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
function findValue($this, value) {
  var i = $this.n5_1;
  $l$loop: while (true) {
    i = i - 1 | 0;
    if (!(i >= 0)) {
      break $l$loop;
    }
    if ($this.k5_1[i] >= 0 && equals(ensureNotNull($this.j5_1)[i], value))
      return i;
  }
  return -1;
}
function addKey($this, key) {
  $this.g7();
  retry: while (true) {
    var hash_0 = hash($this, key);
    var tentativeMaxProbeDistance = coerceAtMost(imul_0($this.m5_1, 2), _get_hashSize__tftcho($this) / 2 | 0);
    var probeDistance = 0;
    while (true) {
      var index = $this.l5_1[hash_0];
      if (index === 0) {
        if ($this.n5_1 >= _get_capacity__a9k9f3($this)) {
          ensureExtraCapacity($this, 1);
          continue retry;
        }
        var _unary__edvuaz = $this.n5_1;
        $this.n5_1 = _unary__edvuaz + 1 | 0;
        var putIndex = _unary__edvuaz;
        $this.i5_1[putIndex] = key;
        $this.k5_1[putIndex] = hash_0;
        $this.l5_1[hash_0] = putIndex + 1 | 0;
        $this.q5_1 = $this.q5_1 + 1 | 0;
        registerModification($this);
        if (probeDistance > $this.m5_1)
          $this.m5_1 = probeDistance;
        return putIndex;
      }
      if (equals($this.i5_1[index - 1 | 0], key)) {
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
  return $this.q5_1 === other.i() && $this.n6(other.k1());
}
var Companion_instance_3;
function Companion_getInstance_3() {
  return Companion_instance_3;
}
var EmptyHolder_instance;
function EmptyHolder_getInstance() {
  if (EmptyHolder_instance === VOID)
    new EmptyHolder();
  return EmptyHolder_instance;
}
function init_kotlin_collections_LinkedHashMap(_this__u8e3s4) {
}
var CompletedContinuation_instance;
function CompletedContinuation_getInstance() {
  return CompletedContinuation_instance;
}
function intercepted(_this__u8e3s4) {
  var tmp0_safe_receiver = _this__u8e3s4 instanceof InterceptedCoroutine ? _this__u8e3s4 : null;
  var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.b9();
  return tmp1_elvis_lhs == null ? _this__u8e3s4 : tmp1_elvis_lhs;
}
function invokeSuspendSuperType(_this__u8e3s4, completion) {
  throw NotImplementedError.n9('It is intrinsic method');
}
function createCoroutineUninterceptedGeneratorVersion(_this__u8e3s4, completion) {
  // Inline function 'kotlin.coroutines.intrinsics.createCoroutineFromGeneratorFunction' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  var continuation = new GeneratorCoroutineImpl(completion);
  var tmp = continuation;
  // Inline function 'kotlin.js.asDynamic' call
  var a = _this__u8e3s4;
  tmp.w8_1 = typeof a === 'function' ? a(continuation) : _this__u8e3s4.o9(continuation);
  return continuation;
}
function *await_0(promise, $completion) {
  // Inline function 'kotlin.coroutines.suspendCoroutine' call
  // Inline function 'kotlin.js.suspendCoroutineUninterceptedOrReturnJS' call
  return yield () => {
    var c = $completion;
    var safe = SafeContinuation.i9(intercepted(c));
    var tmp = await$lambda(safe);
    promise.then(tmp, await$lambda_0(safe));
    return safe.j9();
  };
}
function promisify(fn) {
  return new Promise(promisify$lambda(fn));
}
function await$lambda($continuation) {
  return (result) => {
    // Inline function 'kotlin.coroutines.resume' call
    var this_0 = $continuation;
    // Inline function 'kotlin.Companion.success' call
    var tmp$ret$1 = _Result___init__impl__xyqfz8(result);
    this_0.t8(tmp$ret$1);
    return Unit_instance;
  };
}
function await$lambda_0($continuation) {
  return (error) => {
    // Inline function 'kotlin.coroutines.resumeWithException' call
    var this_0 = $continuation;
    // Inline function 'kotlin.Companion.failure' call
    var tmp$ret$1 = _Result___init__impl__xyqfz8(createFailure(error));
    this_0.t8(tmp$ret$1);
    return Unit_instance;
  };
}
function promisify$lambda($fn) {
  return (resolve, reject) => {
    // Inline function 'kotlin.coroutines.Continuation' call
    var completion = new promisify$1$$inlined$Continuation$1(EmptyCoroutineContext_instance, resolve, reject);
    startCoroutine($fn, completion);
    return Unit_instance;
  };
}
function init_kotlin_UnsupportedOperationException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.j2_1);
}
function init_kotlin_IllegalArgumentException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.a3_1);
}
function init_kotlin_RuntimeException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.d7_1);
}
function init_kotlin_Exception(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.y9_1);
}
function init_kotlin_NoSuchElementException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.w1_1);
}
function init_kotlin_IndexOutOfBoundsException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.ea_1);
}
function init_kotlin_IllegalStateException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.o2_1);
}
function init_kotlin_ConcurrentModificationException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.x6_1);
}
function init_kotlin_ArithmeticException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.ka_1);
}
function init_kotlin_Error(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.na_1);
}
function init_kotlin_NullPointerException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.s2_1);
}
function init_kotlin_ClassCastException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.w2_1);
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
                  tmp_2 = PrimitiveClasses_getInstance().rb();
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
function checkRadix(radix) {
  if (!(2 <= radix ? radix <= 36 : false)) {
    throw IllegalArgumentException.b3('radix ' + radix + ' was not in valid range 2..36');
  }
  return radix;
}
function digitOf(char, radix) {
  // Inline function 'kotlin.let' call
  var it = Char__compareTo_impl_ypi4mb(char, _Char___init__impl__6a9atx(48)) >= 0 && Char__compareTo_impl_ypi4mb(char, _Char___init__impl__6a9atx(57)) <= 0 ? Char__minus_impl_a2frrh(char, _Char___init__impl__6a9atx(48)) : Char__compareTo_impl_ypi4mb(char, _Char___init__impl__6a9atx(65)) >= 0 && Char__compareTo_impl_ypi4mb(char, _Char___init__impl__6a9atx(90)) <= 0 ? Char__minus_impl_a2frrh(char, _Char___init__impl__6a9atx(65)) + 10 | 0 : Char__compareTo_impl_ypi4mb(char, _Char___init__impl__6a9atx(97)) >= 0 && Char__compareTo_impl_ypi4mb(char, _Char___init__impl__6a9atx(122)) <= 0 ? Char__minus_impl_a2frrh(char, _Char___init__impl__6a9atx(97)) + 10 | 0 : Char__compareTo_impl_ypi4mb(char, _Char___init__impl__6a9atx(128)) < 0 ? -1 : Char__compareTo_impl_ypi4mb(char, _Char___init__impl__6a9atx(65313)) >= 0 && Char__compareTo_impl_ypi4mb(char, _Char___init__impl__6a9atx(65338)) <= 0 ? Char__minus_impl_a2frrh(char, _Char___init__impl__6a9atx(65313)) + 10 | 0 : Char__compareTo_impl_ypi4mb(char, _Char___init__impl__6a9atx(65345)) >= 0 && Char__compareTo_impl_ypi4mb(char, _Char___init__impl__6a9atx(65370)) <= 0 ? Char__minus_impl_a2frrh(char, _Char___init__impl__6a9atx(65345)) + 10 | 0 : digitToIntImpl(char);
  return it >= radix ? -1 : it;
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
    throw IllegalArgumentException.b3(toString_1(message));
  }
  var byteIndex = startIndex;
  var stringBuilder = StringBuilder.d();
  while (byteIndex < endIndex) {
    var _unary__edvuaz = byteIndex;
    byteIndex = _unary__edvuaz + 1 | 0;
    var byte = bytes[_unary__edvuaz];
    if (byte >= 0)
      stringBuilder.i8(numberToChar(byte));
    else if (byte >> 5 === -2) {
      var code = codePointFrom2(bytes, byte, byteIndex, endIndex, throwOnMalformed);
      if (code <= 0) {
        stringBuilder.i8(_Char___init__impl__6a9atx(65533));
        byteIndex = byteIndex + (-code | 0) | 0;
      } else {
        stringBuilder.i8(numberToChar(code));
        byteIndex = byteIndex + 1 | 0;
      }
    } else if (byte >> 4 === -2) {
      var code_0 = codePointFrom3(bytes, byte, byteIndex, endIndex, throwOnMalformed);
      if (code_0 <= 0) {
        stringBuilder.i8(_Char___init__impl__6a9atx(65533));
        byteIndex = byteIndex + (-code_0 | 0) | 0;
      } else {
        stringBuilder.i8(numberToChar(code_0));
        byteIndex = byteIndex + 2 | 0;
      }
    } else if (byte >> 3 === -2) {
      var code_1 = codePointFrom4(bytes, byte, byteIndex, endIndex, throwOnMalformed);
      if (code_1 <= 0) {
        stringBuilder.i8(_Char___init__impl__6a9atx(65533));
        byteIndex = byteIndex + (-code_1 | 0) | 0;
      } else {
        var high = (code_1 - 65536 | 0) >> 10 | 55296;
        var low = code_1 & 1023 | 56320;
        stringBuilder.i8(numberToChar(high));
        stringBuilder.i8(numberToChar(low));
        byteIndex = byteIndex + 3 | 0;
      }
    } else {
      malformed(0, byteIndex, throwOnMalformed);
      stringBuilder.i8(_Char___init__impl__6a9atx(65533));
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
    throw CharacterCodingException.wb('Malformed sequence starting at ' + (index - 1 | 0));
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
  return Companion_getInstance_9().fc(numberToLong((new Date()).getTime()));
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
var Companion_instance_4;
function Companion_getInstance_4() {
  return Companion_instance_4;
}
function toString_2($this, entry) {
  return toString_3($this, entry.c1()) + '=' + toString_3($this, entry.d1());
}
function toString_3($this, o) {
  return o === $this ? '(this Map)' : toString_0(o);
}
function implFindEntry($this, key) {
  var tmp0 = $this.k1();
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
var Companion_instance_5;
function Companion_getInstance_5() {
  return Companion_instance_5;
}
function AbstractMap$toString$lambda(this$0) {
  return (it) => toString_2(this$0, it);
}
var Companion_instance_6;
function Companion_getInstance_6() {
  return Companion_instance_6;
}
function throwIndexOverflow() {
  throw ArithmeticException.ma('Index overflow has happened.');
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
        tmp = toMap_0(_this__u8e3s4, LinkedHashMap.q8(mapCapacity(_this__u8e3s4.i())));
        break;
    }
    return tmp;
  }
  return optimizeReadOnlyMap(toMap_0(_this__u8e3s4, LinkedHashMap.g2()));
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
    var key = _destruct__k2r9zo.dd();
    var value = _destruct__k2r9zo.ed();
    _this__u8e3s4.l2(key, value);
  }
}
function putAll_0(_this__u8e3s4, pairs) {
  var _iterator__ex2g4s = pairs.f();
  while (_iterator__ex2g4s.g()) {
    var _destruct__k2r9zo = _iterator__ex2g4s.h();
    var key = _destruct__k2r9zo.dd();
    var value = _destruct__k2r9zo.ed();
    _this__u8e3s4.l2(key, value);
  }
}
function hashMapOf(pairs) {
  // Inline function 'kotlin.apply' call
  var this_0 = HashMap.v5(mapCapacity(pairs.length));
  putAll(this_0, pairs);
  return this_0;
}
var EmptySet_instance;
function EmptySet_getInstance() {
  return EmptySet_instance;
}
function startCoroutine(_this__u8e3s4, completion) {
  // Inline function 'kotlin.coroutines.resume' call
  var this_0 = intercepted(createCoroutineUninterceptedGeneratorVersion(_this__u8e3s4, completion));
  // Inline function 'kotlin.Companion.success' call
  var tmp$ret$1 = _Result___init__impl__xyqfz8(Unit_instance);
  this_0.t8(tmp$ret$1);
}
var Key_instance;
function Key_getInstance() {
  return Key_instance;
}
var EmptyCoroutineContext_instance;
function EmptyCoroutineContext_getInstance() {
  return EmptyCoroutineContext_instance;
}
function get_COROUTINE_SUSPENDED() {
  return CoroutineSingletons_COROUTINE_SUSPENDED_getInstance();
}
var static_init_called;
function static_init() {
  if (static_init_called)
    return Unit_instance;
  static_init_called = true;
  CoroutineSingletons_COROUTINE_SUSPENDED_instance = new CoroutineSingletons('COROUTINE_SUSPENDED', 0);
  CoroutineSingletons_UNDECIDED_instance = new CoroutineSingletons('UNDECIDED', 1);
  CoroutineSingletons_RESUMED_instance = new CoroutineSingletons('RESUMED', 2);
}
var CoroutineSingletons_COROUTINE_SUSPENDED_instance;
var CoroutineSingletons_UNDECIDED_instance;
var CoroutineSingletons_RESUMED_instance;
function CoroutineSingletons_COROUTINE_SUSPENDED_getInstance() {
  static_init();
  return CoroutineSingletons_COROUTINE_SUSPENDED_instance;
}
function CoroutineSingletons_UNDECIDED_getInstance() {
  static_init();
  return CoroutineSingletons_UNDECIDED_instance;
}
function CoroutineSingletons_RESUMED_getInstance() {
  static_init();
  return CoroutineSingletons_RESUMED_instance;
}
function getProgressionLastElement(start, end, step) {
  var tmp;
  if (step > 0) {
    tmp = start >= end ? end : end - differenceModulo(end, start, step) | 0;
  } else if (step < 0) {
    tmp = start <= end ? end : end + differenceModulo(start, end, -step | 0) | 0;
  } else {
    throw IllegalArgumentException.b3('Step is zero.');
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
var Companion_instance_7;
function Companion_getInstance_7() {
  if (Companion_instance_7 === VOID) {
    Companion_instance_8;
    new Companion_7();
  }
  return Companion_instance_7;
}
var Companion_instance_8;
function Companion_getInstance_8() {
  return Companion_instance_8;
}
function appendElement(_this__u8e3s4, element, transform) {
  if (!(transform == null))
    _this__u8e3s4.e(transform(element));
  else {
    if (element == null ? true : isCharSequence(element))
      _this__u8e3s4.e(element);
    else {
      if (element instanceof Char)
        _this__u8e3s4.i8(element.sd_1);
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
function toLongOrNull(_this__u8e3s4) {
  return toLongOrNull_0(_this__u8e3s4, 10);
}
function toLongOrNull_0(_this__u8e3s4, radix) {
  checkRadix(radix);
  var length = _this__u8e3s4.length;
  if (length === 0)
    return null;
  var start;
  var isNegative;
  var limit;
  var firstChar = charCodeAt(_this__u8e3s4, 0);
  if (Char__compareTo_impl_ypi4mb(firstChar, _Char___init__impl__6a9atx(48)) < 0) {
    if (length === 1)
      return null;
    start = 1;
    if (firstChar === _Char___init__impl__6a9atx(45)) {
      isNegative = true;
      limit = -9223372036854775808n;
    } else if (firstChar === _Char___init__impl__6a9atx(43)) {
      isNegative = false;
      limit = -9223372036854775807n;
    } else
      return null;
  } else {
    start = 0;
    isNegative = false;
    limit = -9223372036854775807n;
  }
  // Inline function 'kotlin.Long.div' call
  var this_0 = -9223372036854775807n;
  var limitForMaxRadix = divide(this_0, fromInt_0(36));
  var limitBeforeMul = limitForMaxRadix;
  var result = 0n;
  var inductionVariable = start;
  if (inductionVariable < length)
    do {
      var i = inductionVariable;
      inductionVariable = inductionVariable + 1 | 0;
      var digit = digitOf(charCodeAt(_this__u8e3s4, i), radix);
      if (digit < 0)
        return null;
      if (result < limitBeforeMul) {
        if (limitBeforeMul === limitForMaxRadix) {
          // Inline function 'kotlin.Long.div' call
          var this_1 = limit;
          limitBeforeMul = divide(this_1, fromInt_0(radix));
          if (result < limitBeforeMul) {
            return null;
          }
        } else {
          return null;
        }
      }
      // Inline function 'kotlin.Long.times' call
      var this_2 = result;
      result = multiply_0(this_2, fromInt_0(radix));
      var tmp = result;
      // Inline function 'kotlin.Long.plus' call
      var this_3 = limit;
      if (tmp < add_0(this_3, fromInt_0(digit)))
        return null;
      // Inline function 'kotlin.Long.minus' call
      var this_4 = result;
      result = subtract_0(this_4, fromInt_0(digit));
    }
     while (inductionVariable < length);
  return isNegative ? result : negate_0(result);
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
var Companion_instance_9;
function Companion_getInstance_9() {
  if (Companion_instance_9 === VOID)
    new Companion_9();
  return Companion_instance_9;
}
function formatIso(instant) {
  _init_properties_Instant_kt__2myitt();
  // Inline function 'kotlin.text.buildString' call
  // Inline function 'kotlin.apply' call
  var this_0 = StringBuilder.d();
  var ldt = Companion_instance_10.zd(instant);
  var number = ldt.ae_1;
  // Inline function 'kotlin.math.absoluteValue' call
  if (abs_0(number) < 1000) {
    var innerBuilder = StringBuilder.d();
    if (number >= 0) {
      // Inline function 'kotlin.text.deleteAt' call
      innerBuilder.zb(number + 10000 | 0).ac(0);
    } else {
      // Inline function 'kotlin.text.deleteAt' call
      innerBuilder.zb(number - 10000 | 0).ac(1);
    }
    this_0.e(innerBuilder);
  } else {
    if (number >= 10000) {
      this_0.i8(_Char___init__impl__6a9atx(43));
    }
    this_0.zb(number);
  }
  this_0.i8(_Char___init__impl__6a9atx(45));
  formatIso$appendTwoDigits(this_0, this_0, ldt.be_1);
  this_0.i8(_Char___init__impl__6a9atx(45));
  formatIso$appendTwoDigits(this_0, this_0, ldt.ce_1);
  this_0.i8(_Char___init__impl__6a9atx(84));
  formatIso$appendTwoDigits(this_0, this_0, ldt.de_1);
  this_0.i8(_Char___init__impl__6a9atx(58));
  formatIso$appendTwoDigits(this_0, this_0, ldt.ee_1);
  this_0.i8(_Char___init__impl__6a9atx(58));
  formatIso$appendTwoDigits(this_0, this_0, ldt.fe_1);
  if (!(ldt.ge_1 === 0)) {
    this_0.i8(_Char___init__impl__6a9atx(46));
    var zerosToStrip = 0;
    while ((ldt.ge_1 % get_POWERS_OF_TEN()[zerosToStrip + 1 | 0] | 0) === 0) {
      zerosToStrip = zerosToStrip + 1 | 0;
    }
    zerosToStrip = zerosToStrip - (zerosToStrip % 3 | 0) | 0;
    var numberToOutput = ldt.ge_1 / get_POWERS_OF_TEN()[zerosToStrip] | 0;
    this_0.h8(substring_0((numberToOutput + get_POWERS_OF_TEN()[9 - zerosToStrip | 0] | 0).toString(), 1));
  }
  this_0.i8(_Char___init__impl__6a9atx(90));
  return this_0.toString();
}
var Companion_instance_10;
function Companion_getInstance_10() {
  return Companion_instance_10;
}
function formatIso$appendTwoDigits(_this__u8e3s4, $this_buildString, number) {
  if (number < 10) {
    _this__u8e3s4.i8(_Char___init__impl__6a9atx(48));
  }
  $this_buildString.zb(number);
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
var static_init_called_0;
function static_init_0() {
  if (static_init_called_0)
    return Unit_instance;
  static_init_called_0 = true;
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
  static_init_0();
  return LazyThreadSafetyMode_PUBLICATION_instance;
}
function _Result___init__impl__xyqfz8(value) {
  return value;
}
function _Result___get_value__impl__bjfvqg($this) {
  return $this;
}
function _Result___get_isSuccess__impl__sndoy8($this) {
  var tmp = _Result___get_value__impl__bjfvqg($this);
  return !(tmp instanceof Failure);
}
function Result__exceptionOrNull_impl_p6xea9($this) {
  var tmp;
  if (_Result___get_value__impl__bjfvqg($this) instanceof Failure) {
    tmp = _Result___get_value__impl__bjfvqg($this).k9_1;
  } else {
    tmp = null;
  }
  return tmp;
}
var Companion_instance_11;
function Companion_getInstance_11() {
  return Companion_instance_11;
}
function createFailure(exception) {
  return new Failure(exception);
}
function to(_this__u8e3s4, that) {
  return new Pair(_this__u8e3s4, that);
}
var Companion_instance_12;
function Companion_getInstance_12() {
  if (Companion_instance_12 === VOID)
    new Companion_12();
  return Companion_instance_12;
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
  return Companion_getInstance_12().oe(randomBytes);
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
    $this$buildSerialDescriptor.ef('type', serializer(StringCompanionObject_instance).ve());
    $this$buildSerialDescriptor.ef('value', buildSerialDescriptor('kotlinx.serialization.Polymorphic<' + this$0.ff_1.qa() + '>', CONTEXTUAL_getInstance(), []));
    $this$buildSerialDescriptor.ye_1 = this$0.gf_1;
    return Unit_instance;
  };
}
function PolymorphicSerializer$descriptor$delegate$lambda(this$0) {
  return () => {
    var tmp = OPEN_getInstance();
    return withContext(buildSerialDescriptor('kotlinx.serialization.Polymorphic', tmp, [], PolymorphicSerializer$descriptor$delegate$lambda$lambda(this$0)), this$0.ff_1);
  };
}
function PolymorphicSerializer$_get_descriptor_$ref_8tw9if() {
  return constructCallableReference((p0) => p0.ve(), 1, 0, 1);
}
function init_kotlinx_serialization_SerializationException(_this__u8e3s4) {
  captureStack(_this__u8e3s4, _this__u8e3s4.wf_1);
}
function get_nullable(_this__u8e3s4) {
  var tmp;
  if (_this__u8e3s4.ve().ag()) {
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
    throw IllegalArgumentException.b3(toString_1(message));
  }
  // Inline function 'kotlin.require' call
  if (!!equals(kind, CLASS_getInstance())) {
    var message_0 = "For StructureKind.CLASS please use 'buildClassSerialDescriptor' instead";
    throw IllegalArgumentException.b3(toString_1(message_0));
  }
  var sdBuilder = new ClassSerialDescriptorBuilder(serialName);
  builder(sdBuilder);
  return new SerialDescriptorImpl(serialName, kind, sdBuilder.ze_1.i(), toList(typeParameters), sdBuilder);
}
function _get__hashCode__tgwhef($this) {
  var tmp0 = $this.yg_1;
  var tmp = KProperty1;
  // Inline function 'kotlin.getValue' call
  getPropertyCallableRef('_hashCode', 1, tmp, SerialDescriptorImpl$_get__hashCode_$ref_2v7wzp(), null);
  return tmp0.d1();
}
function SerialDescriptorImpl$_hashCode$delegate$lambda(this$0) {
  return () => hashCodeImpl(this$0, this$0.xg_1);
}
function SerialDescriptorImpl$_get__hashCode_$ref_2v7wzp() {
  return constructCallableReference((p0) => _get__hashCode__tgwhef(p0), 1, 0, 2);
}
function buildSerialDescriptor$lambda(_this__u8e3s4) {
  return Unit_instance;
}
var BOOLEAN_instance;
function BOOLEAN_getInstance() {
  if (BOOLEAN_instance === VOID)
    new BOOLEAN();
  return BOOLEAN_instance;
}
var INT_instance;
function INT_getInstance() {
  if (INT_instance === VOID)
    new INT();
  return INT_instance;
}
var LONG_instance;
function LONG_getInstance() {
  if (LONG_instance === VOID)
    new LONG();
  return LONG_instance;
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
var LIST_instance;
function LIST_getInstance() {
  if (LIST_instance === VOID)
    new LIST();
  return LIST_instance;
}
var MAP_instance;
function MAP_getInstance() {
  if (MAP_instance === VOID)
    new MAP();
  return MAP_instance;
}
function requireNonNegativeIndex(index, name) {
  if (index < 0)
    throw IndexOutOfBoundsException.ga('Illegal index ' + index + ', ' + name + ' expects only non-negative indices');
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
    return _this__u8e3s4.zg();
  var result = HashSet.q(_this__u8e3s4.gg());
  var inductionVariable = 0;
  var last = _this__u8e3s4.gg();
  if (inductionVariable < last)
    do {
      var i = inductionVariable;
      inductionVariable = inductionVariable + 1 | 0;
      // Inline function 'kotlin.collections.plusAssign' call
      var element = _this__u8e3s4.hg(i);
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
  var missingFields = ArrayList.y4();
  var missingFieldsBits = goldenMask & ~seen;
  var inductionVariable = 0;
  if (inductionVariable < 32)
    do {
      var i = inductionVariable;
      inductionVariable = inductionVariable + 1 | 0;
      if (!((missingFieldsBits & 1) === 0)) {
        // Inline function 'kotlin.collections.plusAssign' call
        var element = descriptor.hg(i);
        missingFields.o(element);
      }
      missingFieldsBits = missingFieldsBits >>> 1 | 0;
    }
     while (inductionVariable < 32);
  throw MissingFieldException.yf(missingFields, descriptor.eg());
}
function _get_childSerializers__7vnyfa($this) {
  var tmp0 = $this.ei_1;
  var tmp = KProperty1;
  // Inline function 'kotlin.getValue' call
  getPropertyCallableRef('childSerializers', 1, tmp, PluginGeneratedSerialDescriptor$_get_childSerializers_$ref_e7suca(), null);
  return tmp0.d1();
}
function _get__hashCode__tgwhef_0($this) {
  var tmp0 = $this.gi_1;
  var tmp = KProperty1;
  // Inline function 'kotlin.getValue' call
  getPropertyCallableRef('_hashCode', 1, tmp, PluginGeneratedSerialDescriptor$_get__hashCode_$ref_cmj4vz(), null);
  return tmp0.d1();
}
function buildIndices($this) {
  var indices = HashMap.h5();
  var inductionVariable = 0;
  var last = $this.zh_1.length - 1 | 0;
  if (inductionVariable <= last)
    do {
      var i = inductionVariable;
      inductionVariable = inductionVariable + 1 | 0;
      // Inline function 'kotlin.collections.set' call
      var key = $this.zh_1[i];
      indices.l2(key, i);
    }
     while (inductionVariable <= last);
  return indices;
}
function PluginGeneratedSerialDescriptor$childSerializers$delegate$lambda(this$0) {
  return () => {
    var tmp0_safe_receiver = this$0.wh_1;
    var tmp1_elvis_lhs = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.hi();
    return tmp1_elvis_lhs == null ? get_EMPTY_SERIALIZER_ARRAY() : tmp1_elvis_lhs;
  };
}
function PluginGeneratedSerialDescriptor$_get_childSerializers_$ref_e7suca() {
  return constructCallableReference((p0) => _get_childSerializers__7vnyfa(p0), 1, 0, 3);
}
function PluginGeneratedSerialDescriptor$typeParameterDescriptors$delegate$lambda(this$0) {
  return () => {
    var tmp0_safe_receiver = this$0.wh_1;
    var tmp1_safe_receiver = tmp0_safe_receiver == null ? null : tmp0_safe_receiver.ii();
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
        var tmp$ret$2 = item.ve();
        destination.o(tmp$ret$2);
      }
      tmp = destination;
    }
    return compactArray(tmp);
  };
}
function PluginGeneratedSerialDescriptor$_get_typeParameterDescriptors_$ref_jk3pka() {
  return constructCallableReference((p0) => p0.ji(), 1, 0, 4);
}
function PluginGeneratedSerialDescriptor$_hashCode$delegate$lambda(this$0) {
  return () => hashCodeImpl(this$0, this$0.ji());
}
function PluginGeneratedSerialDescriptor$_get__hashCode_$ref_cmj4vz() {
  return constructCallableReference((p0) => _get__hashCode__tgwhef_0(p0), 1, 0, 5);
}
function hashCodeImpl(_this__u8e3s4, typeParams) {
  var result = getStringHashCode(_this__u8e3s4.eg());
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
    var tmp0_safe_receiver = element.eg();
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
    var tmp0_safe_receiver_0 = element_0.fg();
    var tmp1_elvis_lhs_0 = tmp0_safe_receiver_0 == null ? null : hashCode(tmp0_safe_receiver_0);
    accumulator_0 = tmp_0 + (tmp1_elvis_lhs_0 == null ? 0 : tmp1_elvis_lhs_0) | 0;
  }
  var kindHash = accumulator_0;
  result = imul_0(31, result) + namesHash | 0;
  result = imul_0(31, result) + kindHash | 0;
  return result;
}
function toStringImpl_0(_this__u8e3s4) {
  var tmp = until(0, _this__u8e3s4.gg());
  var tmp_0 = _this__u8e3s4.eg() + '(';
  return joinToString_1(tmp, ', ', tmp_0, ')', VOID, VOID, toStringImpl$lambda(_this__u8e3s4));
}
function toStringImpl$lambda($this_toStringImpl) {
  return (i) => $this_toStringImpl.hg(i) + ': ' + $this_toStringImpl.ig(i).eg();
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
  throw IllegalStateException.p2('Primitive descriptor ' + $this.ni_1 + ' does not have elements');
}
var LongSerializer_instance;
function LongSerializer_getInstance() {
  if (LongSerializer_instance === VOID)
    new LongSerializer();
  return LongSerializer_instance;
}
var BooleanSerializer_instance;
function BooleanSerializer_getInstance() {
  if (BooleanSerializer_instance === VOID)
    new BooleanSerializer();
  return BooleanSerializer_instance;
}
function getChecked(_this__u8e3s4, index) {
  if (!(0 <= index ? index <= (_this__u8e3s4.length - 1 | 0) : false))
    throw IndexOutOfBoundsException.ga('Index ' + index + ' out of bounds ' + get_indices(_this__u8e3s4).toString());
  return _this__u8e3s4[index];
}
var Companion_instance_13;
function Companion_getInstance_13() {
  if (Companion_instance_13 === VOID)
    new Companion_13();
  return Companion_instance_13;
}
var Companion_instance_14;
function Companion_getInstance_14() {
  return Companion_instance_14;
}
var $serializer_instance;
function $serializer_getInstance() {
  if ($serializer_instance === VOID)
    new $serializer();
  return $serializer_instance;
}
var Companion_instance_15;
function Companion_getInstance_15() {
  return Companion_instance_15;
}
var Companion_instance_16;
function Companion_getInstance_16() {
  if (Companion_instance_16 === VOID)
    new Companion_16();
  return Companion_instance_16;
}
function PageQuery$Companion$$childSerializers$_anonymous__54lki7() {
  var tmp = getKClass(OffsetPaginationDTO);
  // Inline function 'kotlin.arrayOf' call
  // Inline function 'kotlin.js.unsafeCast' call
  // Inline function 'kotlin.js.asDynamic' call
  var tmp$ret$0 = [];
  return PolymorphicSerializer.kf(tmp, tmp$ret$0);
}
var Companion_instance_17;
function Companion_getInstance_17() {
  if (Companion_instance_17 === VOID)
    new Companion_17();
  return Companion_instance_17;
}
var $serializer_instance_0;
function $serializer_getInstance_0() {
  if ($serializer_instance_0 === VOID)
    new $serializer_0();
  return $serializer_instance_0;
}
var Companion_instance_18;
function Companion_getInstance_18() {
  if (Companion_instance_18 === VOID)
    new Companion_18();
  return Companion_instance_18;
}
var Companion_instance_19;
function Companion_getInstance_19() {
  return Companion_instance_19;
}
var Companion_instance_20;
function Companion_getInstance_20() {
  return Companion_instance_20;
}
var $serializer_instance_1;
function $serializer_getInstance_1() {
  if ($serializer_instance_1 === VOID)
    new $serializer_1();
  return $serializer_instance_1;
}
var Companion_instance_21;
function Companion_getInstance_21() {
  return Companion_instance_21;
}
var $serializer_instance_2;
function $serializer_getInstance_2() {
  if ($serializer_instance_2 === VOID)
    new $serializer_2();
  return $serializer_instance_2;
}
var Companion_instance_22;
function Companion_getInstance_22() {
  return Companion_instance_22;
}
var $serializer_instance_3;
function $serializer_getInstance_3() {
  if ($serializer_instance_3 === VOID)
    new $serializer_3();
  return $serializer_instance_3;
}
var Companion_instance_23;
function Companion_getInstance_23() {
  return Companion_instance_23;
}
var $serializer_instance_4;
function $serializer_getInstance_4() {
  if ($serializer_instance_4 === VOID)
    new $serializer_4();
  return $serializer_instance_4;
}
function hasRole(_this__u8e3s4, role) {
  return hasRole_0(_this__u8e3s4, role.identifier);
}
function hasRole_0(_this__u8e3s4, role) {
  // Inline function 'kotlin.collections.orEmpty' call
  var tmp0_elvis_lhs = _this__u8e3s4.roles;
  var tmp;
  if (tmp0_elvis_lhs == null) {
    // Inline function 'kotlin.emptyArray' call
    tmp = [];
  } else {
    tmp = tmp0_elvis_lhs;
  }
  var tmp$ret$0 = tmp;
  return contains(tmp$ret$0, role);
}
function hasOneOfRoles(_this__u8e3s4, roles) {
  // Inline function 'kotlin.collections.map' call
  // Inline function 'kotlin.collections.mapTo' call
  var destination = ArrayList.m(roles.length);
  var inductionVariable = 0;
  var last = roles.length;
  while (inductionVariable < last) {
    var item = roles[inductionVariable];
    inductionVariable = inductionVariable + 1 | 0;
    var tmp$ret$2 = item.identifier;
    destination.o(tmp$ret$2);
  }
  // Inline function 'kotlin.collections.orEmpty' call
  var tmp0_elvis_lhs = _this__u8e3s4.roles;
  var tmp;
  if (tmp0_elvis_lhs == null) {
    // Inline function 'kotlin.emptyArray' call
    tmp = [];
  } else {
    tmp = tmp0_elvis_lhs;
  }
  var tmp2_any_this = tmp;
  var tmp$ret$5;
  $l$block_0: {
    // Inline function 'kotlin.collections.any' call
    var tmp_0;
    if (isInterface(destination, Collection)) {
      tmp_0 = destination.s();
    } else {
      tmp_0 = false;
    }
    if (tmp_0) {
      tmp$ret$5 = false;
      break $l$block_0;
    }
    var _iterator__ex2g4s = destination.f();
    while (_iterator__ex2g4s.g()) {
      var element = _iterator__ex2g4s.h();
      if (contains(tmp2_any_this, element)) {
        tmp$ret$5 = true;
        break $l$block_0;
      }
    }
    tmp$ret$5 = false;
  }
  return tmp$ret$5;
}
var static_init_called_1;
function static_init_1() {
  if (static_init_called_1)
    return Unit_instance;
  static_init_called_1 = true;
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
  static_init_1();
  return [ImPermission_IM_FORCE_MFA_OTP_getInstance(), ImPermission_IM_USER_READ_getInstance(), ImPermission_IM_USER_ROLE_WRITE_getInstance(), ImPermission_IM_USER_WRITE_getInstance(), ImPermission_IM_MY_USER_getInstance(), ImPermission_IM_ORGANIZATION_READ_getInstance(), ImPermission_IM_ORGANIZATION_WRITE_getInstance(), ImPermission_IM_MY_ORGANIZATION_WRITE_getInstance(), ImPermission_IM_MY_ORGANIZATION_READ_getInstance(), ImPermission_IM_ORGANIZATION_API_KEY_READ_getInstance(), ImPermission_IM_ORGANIZATION_STATUS_WRITE_getInstance(), ImPermission_IM_APIKEY_READ_getInstance(), ImPermission_IM_APIKEY_WRITE_getInstance(), ImPermission_IM_SPACE_READ_getInstance(), ImPermission_IM_SPACE_WRITE_getInstance(), ImPermission_IM_ROLE_READ_getInstance(), ImPermission_IM_ROLE_WRITE_getInstance()];
}
function valueOf(value) {
  static_init_1();
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
  static_init_1();
  return ImPermission_IM_FORCE_MFA_OTP_instance;
}
function ImPermission_IM_USER_READ_getInstance() {
  static_init_1();
  return ImPermission_IM_USER_READ_instance;
}
function ImPermission_IM_USER_ROLE_WRITE_getInstance() {
  static_init_1();
  return ImPermission_IM_USER_ROLE_WRITE_instance;
}
function ImPermission_IM_USER_WRITE_getInstance() {
  static_init_1();
  return ImPermission_IM_USER_WRITE_instance;
}
function ImPermission_IM_MY_USER_getInstance() {
  static_init_1();
  return ImPermission_IM_MY_USER_instance;
}
function ImPermission_IM_ORGANIZATION_READ_getInstance() {
  static_init_1();
  return ImPermission_IM_ORGANIZATION_READ_instance;
}
function ImPermission_IM_ORGANIZATION_WRITE_getInstance() {
  static_init_1();
  return ImPermission_IM_ORGANIZATION_WRITE_instance;
}
function ImPermission_IM_MY_ORGANIZATION_WRITE_getInstance() {
  static_init_1();
  return ImPermission_IM_MY_ORGANIZATION_WRITE_instance;
}
function ImPermission_IM_MY_ORGANIZATION_READ_getInstance() {
  static_init_1();
  return ImPermission_IM_MY_ORGANIZATION_READ_instance;
}
function ImPermission_IM_ORGANIZATION_API_KEY_READ_getInstance() {
  static_init_1();
  return ImPermission_IM_ORGANIZATION_API_KEY_READ_instance;
}
function ImPermission_IM_ORGANIZATION_STATUS_WRITE_getInstance() {
  static_init_1();
  return ImPermission_IM_ORGANIZATION_STATUS_WRITE_instance;
}
function ImPermission_IM_APIKEY_READ_getInstance() {
  static_init_1();
  return ImPermission_IM_APIKEY_READ_instance;
}
function ImPermission_IM_APIKEY_WRITE_getInstance() {
  static_init_1();
  return ImPermission_IM_APIKEY_WRITE_instance;
}
function ImPermission_IM_SPACE_READ_getInstance() {
  static_init_1();
  return ImPermission_IM_SPACE_READ_instance;
}
function ImPermission_IM_SPACE_WRITE_getInstance() {
  static_init_1();
  return ImPermission_IM_SPACE_WRITE_instance;
}
function ImPermission_IM_ROLE_READ_getInstance() {
  static_init_1();
  return ImPermission_IM_ROLE_READ_instance;
}
function ImPermission_IM_ROLE_WRITE_getInstance() {
  static_init_1();
  return ImPermission_IM_ROLE_WRITE_instance;
}
var ExceptionCodes_instance;
function ExceptionCodes_getInstance() {
  return ExceptionCodes_instance;
}
var Companion_instance_24;
function Companion_getInstance_24() {
  return Companion_instance_24;
}
var $serializer_instance_5;
function $serializer_getInstance_5() {
  if ($serializer_instance_5 === VOID)
    new $serializer_5();
  return $serializer_instance_5;
}
var PrivilegePolicies_instance;
function PrivilegePolicies_getInstance() {
  return PrivilegePolicies_instance;
}
var Companion_instance_25;
function Companion_getInstance_25() {
  return Companion_instance_25;
}
var $serializer_instance_6;
function $serializer_getInstance_6() {
  if ($serializer_instance_6 === VOID)
    new $serializer_6();
  return $serializer_instance_6;
}
var Companion_instance_26;
function Companion_getInstance_26() {
  return Companion_instance_26;
}
var $serializer_instance_7;
function $serializer_getInstance_7() {
  if ($serializer_instance_7 === VOID)
    new $serializer_7();
  return $serializer_instance_7;
}
var Companion_instance_27;
function Companion_getInstance_27() {
  return Companion_instance_27;
}
var $serializer_instance_8;
function $serializer_getInstance_8() {
  if ($serializer_instance_8 === VOID)
    new $serializer_8();
  return $serializer_instance_8;
}
var Companion_instance_28;
function Companion_getInstance_28() {
  return Companion_instance_28;
}
var $serializer_instance_9;
function $serializer_getInstance_9() {
  if ($serializer_instance_9 === VOID)
    new $serializer_9();
  return $serializer_instance_9;
}
var Companion_instance_29;
function Companion_getInstance_29() {
  return Companion_instance_29;
}
var $serializer_instance_10;
function $serializer_getInstance_10() {
  if ($serializer_instance_10 === VOID)
    new $serializer_10();
  return $serializer_instance_10;
}
var Companion_instance_30;
function Companion_getInstance_30() {
  return Companion_instance_30;
}
var $serializer_instance_11;
function $serializer_getInstance_11() {
  if ($serializer_instance_11 === VOID)
    new $serializer_11();
  return $serializer_instance_11;
}
function FeatureListResult$Companion$$childSerializers$_anonymous__ad24xl() {
  return new ArrayListSerializer($serializer_getInstance_8());
}
var Companion_instance_31;
function Companion_getInstance_31() {
  if (Companion_instance_31 === VOID)
    new Companion_31();
  return Companion_instance_31;
}
var $serializer_instance_12;
function $serializer_getInstance_12() {
  if ($serializer_instance_12 === VOID)
    new $serializer_12();
  return $serializer_instance_12;
}
function PermissionDefineCommand$Companion$$childSerializers$_anonymous__iqcvl5() {
  return new ArrayListSerializer(new ArrayListSerializer(StringSerializer_getInstance()));
}
var Companion_instance_32;
function Companion_getInstance_32() {
  if (Companion_instance_32 === VOID)
    new Companion_32();
  return Companion_instance_32;
}
var $serializer_instance_13;
function $serializer_getInstance_13() {
  if ($serializer_instance_13 === VOID)
    new $serializer_13();
  return $serializer_instance_13;
}
var Companion_instance_33;
function Companion_getInstance_33() {
  return Companion_instance_33;
}
var $serializer_instance_14;
function $serializer_getInstance_14() {
  if ($serializer_instance_14 === VOID)
    new $serializer_14();
  return $serializer_instance_14;
}
function Permission$Companion$$childSerializers$_anonymous__yuaogr() {
  return new ArrayListSerializer(new ArrayListSerializer(StringSerializer_getInstance()));
}
var Companion_instance_34;
function Companion_getInstance_34() {
  if (Companion_instance_34 === VOID)
    new Companion_34();
  return Companion_instance_34;
}
var $serializer_instance_15;
function $serializer_getInstance_15() {
  if ($serializer_instance_15 === VOID)
    new $serializer_15();
  return $serializer_instance_15;
}
var Companion_instance_35;
function Companion_getInstance_35() {
  return Companion_instance_35;
}
var $serializer_instance_16;
function $serializer_getInstance_16() {
  if ($serializer_instance_16 === VOID)
    new $serializer_16();
  return $serializer_instance_16;
}
var Companion_instance_36;
function Companion_getInstance_36() {
  return Companion_instance_36;
}
var $serializer_instance_17;
function $serializer_getInstance_17() {
  if ($serializer_instance_17 === VOID)
    new $serializer_17();
  return $serializer_instance_17;
}
var Companion_instance_37;
function Companion_getInstance_37() {
  return Companion_instance_37;
}
var $serializer_instance_18;
function $serializer_getInstance_18() {
  if ($serializer_instance_18 === VOID)
    new $serializer_18();
  return $serializer_instance_18;
}
function PermissionListResult$Companion$$childSerializers$_anonymous__atvy5c() {
  return new ArrayListSerializer($serializer_getInstance_15());
}
var Companion_instance_38;
function Companion_getInstance_38() {
  if (Companion_instance_38 === VOID)
    new Companion_38();
  return Companion_instance_38;
}
var $serializer_instance_19;
function $serializer_getInstance_19() {
  if ($serializer_instance_19 === VOID)
    new $serializer_19();
  return $serializer_instance_19;
}
function RoleDefineCommand$Companion$$childSerializers$_anonymous__bp9fbk() {
  return new ArrayListSerializer(StringSerializer_getInstance());
}
function RoleDefineCommand$Companion$$childSerializers$_anonymous__bp9fbk_0() {
  return new LinkedHashMapSerializer(StringSerializer_getInstance(), StringSerializer_getInstance());
}
function RoleDefineCommand$Companion$$childSerializers$_anonymous__bp9fbk_1() {
  return new LinkedHashMapSerializer(StringSerializer_getInstance(), new ArrayListSerializer(StringSerializer_getInstance()));
}
function RoleDefineCommand$Companion$$childSerializers$_anonymous__bp9fbk_2() {
  return new ArrayListSerializer(StringSerializer_getInstance());
}
var Companion_instance_39;
function Companion_getInstance_39() {
  if (Companion_instance_39 === VOID)
    new Companion_39();
  return Companion_instance_39;
}
var $serializer_instance_20;
function $serializer_getInstance_20() {
  if ($serializer_instance_20 === VOID)
    new $serializer_20();
  return $serializer_instance_20;
}
var Companion_instance_40;
function Companion_getInstance_40() {
  return Companion_instance_40;
}
var $serializer_instance_21;
function $serializer_getInstance_21() {
  if ($serializer_instance_21 === VOID)
    new $serializer_21();
  return $serializer_instance_21;
}
function Role$Companion$$childSerializers$_anonymous__76f9e4() {
  return new ArrayListSerializer(StringSerializer_getInstance());
}
function Role$Companion$$childSerializers$_anonymous__76f9e4_0() {
  return new LinkedHashMapSerializer(StringSerializer_getInstance(), StringSerializer_getInstance());
}
function Role$Companion$$childSerializers$_anonymous__76f9e4_1() {
  return new LinkedHashMapSerializer(StringSerializer_getInstance(), new ArrayListSerializer($serializer_getInstance_22()));
}
function Role$Companion$$childSerializers$_anonymous__76f9e4_2() {
  return new ArrayListSerializer(StringSerializer_getInstance());
}
var Companion_instance_41;
function Companion_getInstance_41() {
  if (Companion_instance_41 === VOID)
    new Companion_41();
  return Companion_instance_41;
}
var $serializer_instance_22;
function $serializer_getInstance_22() {
  if ($serializer_instance_22 === VOID)
    new $serializer_22();
  return $serializer_instance_22;
}
var RoleTargetValues_instance;
function RoleTargetValues_getInstance() {
  return RoleTargetValues_instance;
}
var Companion_instance_42;
function Companion_getInstance_42() {
  return Companion_instance_42;
}
var $serializer_instance_23;
function $serializer_getInstance_23() {
  if ($serializer_instance_23 === VOID)
    new $serializer_23();
  return $serializer_instance_23;
}
var Companion_instance_43;
function Companion_getInstance_43() {
  return Companion_instance_43;
}
var $serializer_instance_24;
function $serializer_getInstance_24() {
  if ($serializer_instance_24 === VOID)
    new $serializer_24();
  return $serializer_instance_24;
}
var Companion_instance_44;
function Companion_getInstance_44() {
  return Companion_instance_44;
}
var $serializer_instance_25;
function $serializer_getInstance_25() {
  if ($serializer_instance_25 === VOID)
    new $serializer_25();
  return $serializer_instance_25;
}
function RoleListResult$Companion$$childSerializers$_anonymous__t7zd89() {
  return new ArrayListSerializer($serializer_getInstance_22());
}
var Companion_instance_45;
function Companion_getInstance_45() {
  if (Companion_instance_45 === VOID)
    new Companion_45();
  return Companion_instance_45;
}
var $serializer_instance_26;
function $serializer_getInstance_26() {
  if ($serializer_instance_26 === VOID)
    new $serializer_26();
  return $serializer_instance_26;
}
function asFlow(_this__u8e3s4) {
  // Inline function 'kotlinx.coroutines.flow.internal.unsafeFlow' call
  return new asFlow$$inlined$unsafeFlow$1(_this__u8e3s4);
}
function get_DEFAULT_CONCURRENCY() {
  _init_properties_Merge_kt__cbpr96();
  return DEFAULT_CONCURRENCY;
}
var DEFAULT_CONCURRENCY;
var properties_initialized_Merge_kt_dhn6vs;
function _init_properties_Merge_kt__cbpr96() {
  if (!properties_initialized_Merge_kt_dhn6vs) {
    properties_initialized_Merge_kt_dhn6vs = true;
    DEFAULT_CONCURRENCY = systemProp('kotlinx.coroutines.flow.defaultConcurrency', 16, 1, 2147483647);
  }
}
function *toList_0(_this__u8e3s4, destination, $completion) {
  destination = destination === VOID ? ArrayList.y4() : destination;
  return yield* toCollection_0(_this__u8e3s4, destination, $completion);
}
function *toCollection_0(_this__u8e3s4, destination, $completion) {
  var tmp = toCollection$slambda(destination);
  yield* _this__u8e3s4.yq(new sam$kotlinx_coroutines_flow_FlowCollector$0(tmp), $completion);
  return destination;
}
function toCollection$slambda($destination) {
  return constructCallableReference((value, $completion) => orPromise($completion, function *($completion) {
    $destination.o(value);
    return Unit_instance;
  }), 1);
}
function systemProp(propertyName, defaultValue, minValue, maxValue) {
  minValue = minValue === VOID ? 1 : minValue;
  maxValue = maxValue === VOID ? 2147483647 : maxValue;
  return convertToInt(systemProp_0(propertyName, fromInt_0(defaultValue), fromInt_0(minValue), fromInt_0(maxValue)));
}
function systemProp_0(propertyName, defaultValue, minValue, maxValue) {
  minValue = minValue === VOID ? 1n : minValue;
  maxValue = maxValue === VOID ? 9223372036854775807n : maxValue;
  var tmp0_elvis_lhs = systemProp_1(propertyName);
  var tmp;
  if (tmp0_elvis_lhs == null) {
    return defaultValue;
  } else {
    tmp = tmp0_elvis_lhs;
  }
  var value = tmp;
  var tmp1_elvis_lhs = toLongOrNull(value);
  var tmp_0;
  if (tmp1_elvis_lhs == null) {
    // Inline function 'kotlin.error' call
    var message = "System property '" + propertyName + "' has unrecognized value '" + value + "'";
    throw IllegalStateException.p2(toString_1(message));
  } else {
    tmp_0 = tmp1_elvis_lhs;
  }
  var parsed = tmp_0;
  if (!(minValue <= parsed ? parsed <= maxValue : false)) {
    // Inline function 'kotlin.error' call
    var message_0 = "System property '" + propertyName + "' should be in range " + minValue.toString() + '..' + maxValue.toString() + ", but is '" + parsed.toString() + "'";
    throw IllegalStateException.p2(toString_1(message_0));
  }
  return parsed;
}
function systemProp_1(propertyName) {
  return null;
}
function get_BATCH_DEFAULT_CONCURRENCY() {
  _init_properties_batch_kt__hj63o8();
  return BATCH_DEFAULT_CONCURRENCY;
}
var BATCH_DEFAULT_CONCURRENCY;
var Companion_instance_46;
function Companion_getInstance_46() {
  return Companion_instance_46;
}
var $serializer_instance_27;
function $serializer_getInstance_27() {
  if ($serializer_instance_27 === VOID)
    new $serializer_27();
  return $serializer_instance_27;
}
var properties_initialized_batch_kt_7asg5m;
function _init_properties_batch_kt__hj63o8() {
  if (!properties_initialized_batch_kt_7asg5m) {
    properties_initialized_batch_kt_7asg5m = true;
    BATCH_DEFAULT_CONCURRENCY = get_DEFAULT_CONCURRENCY();
  }
}
function invokeF2Function(fnc, msgs) {
  return promisify(($completion) => invokeF2Function_0(fnc, msgs, $completion));
}
function *invokeF2Function_0(fnc, msgs, $completion) {
  // Inline function 'kotlin.collections.toTypedArray' call
  var this_0 = yield* toList_0(yield* fnc(asFlow(asList(msgs)), $completion), VOID, $completion);
  return copyToArray(this_0);
}
function invokeF2Consumer(fnc, msgs) {
  return promisify(($completion) => invokeF2Consumer_0(fnc, msgs, $completion));
}
function *invokeF2Consumer_0(fnc, msgs, $completion) {
  yield* fnc(asFlow(asList(msgs)), $completion);
  return Unit_instance;
}
function invokeF2Supplier(fnc) {
  return promisify(($completion) => invokeF2Supplier_0(fnc, $completion));
}
function *invokeF2Supplier_0(fnc, $completion) {
  // Inline function 'kotlin.collections.toTypedArray' call
  var this_0 = yield* toList_0(yield* fnc($completion), VOID, $completion);
  return copyToArray(this_0);
}
function invokeF2SupplierSingle(fnc) {
  return promisify(($completion) => invokeF2SupplierSingle_0(fnc, $completion));
}
function *invokeF2SupplierSingle_0(fnc, $completion) {
  return yield* fnc($completion);
}
var Companion_instance_47;
function Companion_getInstance_47() {
  return Companion_instance_47;
}
var $serializer_instance_28;
function $serializer_getInstance_28() {
  if ($serializer_instance_28 === VOID)
    new $serializer_28();
  return $serializer_instance_28;
}
function OrganizationCreateCommand$Companion$$childSerializers$_anonymous__9qbm7i() {
  return new ArrayListSerializer(StringSerializer_getInstance());
}
function OrganizationCreateCommand$Companion$$childSerializers$_anonymous__9qbm7i_0() {
  return new LinkedHashMapSerializer(StringSerializer_getInstance(), StringSerializer_getInstance());
}
var Companion_instance_48;
function Companion_getInstance_48() {
  if (Companion_instance_48 === VOID)
    new Companion_48();
  return Companion_instance_48;
}
var $serializer_instance_29;
function $serializer_getInstance_29() {
  if ($serializer_instance_29 === VOID)
    new $serializer_29();
  return $serializer_instance_29;
}
var Companion_instance_49;
function Companion_getInstance_49() {
  return Companion_instance_49;
}
var $serializer_instance_30;
function $serializer_getInstance_30() {
  if ($serializer_instance_30 === VOID)
    new $serializer_30();
  return $serializer_instance_30;
}
function OrganizationDisableCommand$Companion$$childSerializers$_anonymous__hrk94c() {
  return new LinkedHashMapSerializer(StringSerializer_getInstance(), StringSerializer_getInstance());
}
function OrganizationDisableCommand$Companion$$childSerializers$_anonymous__hrk94c_0() {
  return new LinkedHashMapSerializer(StringSerializer_getInstance(), StringSerializer_getInstance());
}
var Companion_instance_50;
function Companion_getInstance_50() {
  if (Companion_instance_50 === VOID)
    new Companion_50();
  return Companion_instance_50;
}
var $serializer_instance_31;
function $serializer_getInstance_31() {
  if ($serializer_instance_31 === VOID)
    new $serializer_31();
  return $serializer_instance_31;
}
function OrganizationDisabledEvent$Companion$$childSerializers$_anonymous__ds5xzz() {
  return new ArrayListSerializer(StringSerializer_getInstance());
}
var Companion_instance_51;
function Companion_getInstance_51() {
  if (Companion_instance_51 === VOID)
    new Companion_51();
  return Companion_instance_51;
}
var $serializer_instance_32;
function $serializer_getInstance_32() {
  if ($serializer_instance_32 === VOID)
    new $serializer_32();
  return $serializer_instance_32;
}
function OrganizationUpdateCommand$Companion$$childSerializers$_anonymous__c5p751() {
  return new ArrayListSerializer(StringSerializer_getInstance());
}
function OrganizationUpdateCommand$Companion$$childSerializers$_anonymous__c5p751_0() {
  return new LinkedHashMapSerializer(StringSerializer_getInstance(), StringSerializer_getInstance());
}
var Companion_instance_52;
function Companion_getInstance_52() {
  if (Companion_instance_52 === VOID)
    new Companion_52();
  return Companion_instance_52;
}
var $serializer_instance_33;
function $serializer_getInstance_33() {
  if ($serializer_instance_33 === VOID)
    new $serializer_33();
  return $serializer_instance_33;
}
var Companion_instance_53;
function Companion_getInstance_53() {
  return Companion_instance_53;
}
var $serializer_instance_34;
function $serializer_getInstance_34() {
  if ($serializer_instance_34 === VOID)
    new $serializer_34();
  return $serializer_instance_34;
}
function Organization$Companion$$childSerializers$_anonymous__5lsoon() {
  return new LinkedHashMapSerializer(StringSerializer_getInstance(), StringSerializer_getInstance());
}
function Organization$Companion$$childSerializers$_anonymous__5lsoon_0() {
  return new ArrayListSerializer($serializer_getInstance_22());
}
var Companion_instance_54;
function Companion_getInstance_54() {
  if (Companion_instance_54 === VOID)
    new Companion_54();
  return Companion_instance_54;
}
var $serializer_instance_35;
function $serializer_getInstance_35() {
  if ($serializer_instance_35 === VOID)
    new $serializer_35();
  return $serializer_instance_35;
}
function OrganizationRef$Companion$$childSerializers$_anonymous__wrc7pm() {
  return new ArrayListSerializer(StringSerializer_getInstance());
}
var Companion_instance_55;
function Companion_getInstance_55() {
  if (Companion_instance_55 === VOID)
    new Companion_55();
  return Companion_instance_55;
}
var $serializer_instance_36;
function $serializer_getInstance_36() {
  if ($serializer_instance_36 === VOID)
    new $serializer_36();
  return $serializer_instance_36;
}
var OrganizationStatusValues_instance;
function OrganizationStatusValues_getInstance() {
  return OrganizationStatusValues_instance;
}
var OrganizationPolicies_instance;
function OrganizationPolicies_getInstance() {
  return OrganizationPolicies_instance;
}
var Companion_instance_56;
function Companion_getInstance_56() {
  return Companion_instance_56;
}
var $serializer_instance_37;
function $serializer_getInstance_37() {
  if ($serializer_instance_37 === VOID)
    new $serializer_37();
  return $serializer_instance_37;
}
var Companion_instance_57;
function Companion_getInstance_57() {
  return Companion_instance_57;
}
var $serializer_instance_38;
function $serializer_getInstance_38() {
  if ($serializer_instance_38 === VOID)
    new $serializer_38();
  return $serializer_instance_38;
}
var Companion_instance_58;
function Companion_getInstance_58() {
  return Companion_instance_58;
}
var $serializer_instance_39;
function $serializer_getInstance_39() {
  if ($serializer_instance_39 === VOID)
    new $serializer_39();
  return $serializer_instance_39;
}
var Companion_instance_59;
function Companion_getInstance_59() {
  return Companion_instance_59;
}
var $serializer_instance_40;
function $serializer_getInstance_40() {
  if ($serializer_instance_40 === VOID)
    new $serializer_40();
  return $serializer_instance_40;
}
function OrganizationPageQuery$Companion$$childSerializers$_anonymous__umqvvo() {
  return new LinkedHashMapSerializer(StringSerializer_getInstance(), StringSerializer_getInstance());
}
function OrganizationPageQuery$Companion$$childSerializers$_anonymous__umqvvo_0() {
  return new ArrayListSerializer(StringSerializer_getInstance());
}
function OrganizationPageQuery$Companion$$childSerializers$_anonymous__umqvvo_1() {
  return new ArrayListSerializer(StringSerializer_getInstance());
}
var Companion_instance_60;
function Companion_getInstance_60() {
  if (Companion_instance_60 === VOID)
    new Companion_60();
  return Companion_instance_60;
}
var $serializer_instance_41;
function $serializer_getInstance_41() {
  if ($serializer_instance_41 === VOID)
    new $serializer_41();
  return $serializer_instance_41;
}
function OrganizationPageResult$Companion$$childSerializers$_anonymous__sz1e6j() {
  return new ArrayListSerializer($serializer_getInstance_35());
}
var Companion_instance_61;
function Companion_getInstance_61() {
  if (Companion_instance_61 === VOID)
    new Companion_61();
  return Companion_instance_61;
}
var $serializer_instance_42;
function $serializer_getInstance_42() {
  if ($serializer_instance_42 === VOID)
    new $serializer_42();
  return $serializer_instance_42;
}
var Companion_instance_62;
function Companion_getInstance_62() {
  return Companion_instance_62;
}
var $serializer_instance_43;
function $serializer_getInstance_43() {
  if ($serializer_instance_43 === VOID)
    new $serializer_43();
  return $serializer_instance_43;
}
var Companion_instance_63;
function Companion_getInstance_63() {
  return Companion_instance_63;
}
var $serializer_instance_44;
function $serializer_getInstance_44() {
  if ($serializer_instance_44 === VOID)
    new $serializer_44();
  return $serializer_instance_44;
}
var Companion_instance_64;
function Companion_getInstance_64() {
  return Companion_instance_64;
}
var $serializer_instance_45;
function $serializer_getInstance_45() {
  if ($serializer_instance_45 === VOID)
    new $serializer_45();
  return $serializer_instance_45;
}
function OrganizationRefListResult$Companion$$childSerializers$_anonymous__k7o9rl() {
  return new ArrayListSerializer($serializer_getInstance_36());
}
var Companion_instance_65;
function Companion_getInstance_65() {
  if (Companion_instance_65 === VOID)
    new Companion_65();
  return Companion_instance_65;
}
var $serializer_instance_46;
function $serializer_getInstance_46() {
  if ($serializer_instance_46 === VOID)
    new $serializer_46();
  return $serializer_instance_46;
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
initMetadataForCompanion(Companion_0);
initMetadataForInterface(KtMap, 'Map');
initMetadataForCompanion(Companion_1);
initMetadataForClass(Enum, 'Enum');
initMetadataForClass(Long, 'Long');
initMetadataForInterface(FunctionAdapter, 'FunctionAdapter');
initMetadataForClass(arrayIterator$1);
initMetadataForClass(JsArrayView, 'JsArrayView', JsArrayView);
initMetadataForClass(JsMapView, 'JsMapView', JsMapView);
initMetadataForObject(StringCompanionObject, 'StringCompanionObject');
initMetadataForObject(Digit, 'Digit');
initMetadataForInterface(Comparator, 'Comparator');
initMetadataForObject(Unit, 'Unit');
initMetadataForClass(AbstractCollection, 'AbstractCollection', VOID, VOID, [Collection]);
initMetadataForClass(AbstractMutableCollection, 'AbstractMutableCollection', VOID, VOID, [Collection]);
initMetadataForClass(IteratorImpl, 'IteratorImpl');
initMetadataForClass(ListIteratorImpl, 'ListIteratorImpl');
protoOf(AbstractMutableList).asJsReadonlyArrayView = asJsReadonlyArrayView;
initMetadataForClass(AbstractMutableList, 'AbstractMutableList', VOID, VOID, [KtList, Collection]);
initMetadataForClass(SubList, 'SubList');
protoOf(AbstractMap).asJsReadonlyMapView = asJsReadonlyMapView;
initMetadataForClass(AbstractMap, 'AbstractMap', VOID, VOID, [KtMap]);
initMetadataForClass(AbstractMutableMap, 'AbstractMutableMap', VOID, VOID, [KtMap]);
initMetadataForClass(AbstractMutableSet, 'AbstractMutableSet', VOID, VOID, [KtSet, Collection]);
initMetadataForCompanion(Companion_2);
initMetadataForClass(ArrayList, 'ArrayList', ArrayList.y4, VOID, [KtList, Collection]);
initMetadataForClass(HashMap, 'HashMap', HashMap.h5, VOID, [KtMap]);
initMetadataForClass(HashMapKeys, 'HashMapKeys', VOID, VOID, [KtSet, Collection]);
initMetadataForClass(HashMapValues, 'HashMapValues', VOID, VOID, [Collection]);
initMetadataForClass(HashMapEntrySetBase, 'HashMapEntrySetBase', VOID, VOID, [KtSet, Collection]);
initMetadataForClass(HashMapEntrySet, 'HashMapEntrySet');
initMetadataForClass(HashMapKeysDefault$iterator$1);
initMetadataForClass(HashMapKeysDefault, 'HashMapKeysDefault');
initMetadataForClass(HashMapValuesDefault$iterator$1);
initMetadataForClass(HashMapValuesDefault, 'HashMapValuesDefault');
initMetadataForClass(HashSet, 'HashSet', HashSet.t6, VOID, [KtSet, Collection]);
initMetadataForCompanion(Companion_3);
initMetadataForClass(Itr, 'Itr');
initMetadataForClass(KeysItr, 'KeysItr');
initMetadataForClass(ValuesItr, 'ValuesItr');
initMetadataForClass(EntriesItr, 'EntriesItr');
initMetadataForClass(EntryRef, 'EntryRef', VOID, VOID, [Entry]);
initMetadataForInterface(InternalMap, 'InternalMap');
protoOf(InternalHashMap).n6 = containsAllEntries;
initMetadataForClass(InternalHashMap, 'InternalHashMap', InternalHashMap.s5, VOID, [InternalMap]);
initMetadataForObject(EmptyHolder, 'EmptyHolder');
initMetadataForClass(LinkedHashMap, 'LinkedHashMap', LinkedHashMap.g2, VOID, [KtMap]);
initMetadataForInterface(Continuation, 'Continuation');
initMetadataForObject(CompletedContinuation, 'CompletedContinuation', VOID, VOID, [Continuation]);
initMetadataForClass(InterceptedCoroutine, 'InterceptedCoroutine', VOID, VOID, [Continuation]);
initMetadataForClass(GeneratorCoroutineImpl, 'GeneratorCoroutineImpl', VOID, VOID, [Continuation]);
initMetadataForClass(SafeContinuation, 'SafeContinuation', VOID, VOID, [Continuation]);
initMetadataForClass(promisify$1$$inlined$Continuation$1, VOID, VOID, VOID, [Continuation]);
initMetadataForClass(Exception, 'Exception', Exception.z9);
initMetadataForClass(RuntimeException, 'RuntimeException', RuntimeException.s9);
initMetadataForClass(UnsupportedOperationException, 'UnsupportedOperationException', UnsupportedOperationException.k2);
initMetadataForClass(IllegalArgumentException, 'IllegalArgumentException', IllegalArgumentException.v9);
initMetadataForClass(NoSuchElementException, 'NoSuchElementException', NoSuchElementException.j3);
initMetadataForClass(IndexOutOfBoundsException, 'IndexOutOfBoundsException', IndexOutOfBoundsException.fa);
initMetadataForClass(IllegalStateException, 'IllegalStateException', IllegalStateException.ha);
initMetadataForClass(ConcurrentModificationException, 'ConcurrentModificationException', ConcurrentModificationException.r7);
initMetadataForClass(ArithmeticException, 'ArithmeticException', ArithmeticException.la);
initMetadataForClass(Error_0, 'Error', Error_0.oa);
initMetadataForClass(NullPointerException, 'NullPointerException', NullPointerException.t2);
initMetadataForClass(ClassCastException, 'ClassCastException', ClassCastException.x2);
initMetadataForInterface(KClass, 'KClass');
initMetadataForClass(KClassImpl, 'KClassImpl', VOID, VOID, [KClass]);
initMetadataForClass(PrimitiveKClassImpl, 'PrimitiveKClassImpl');
initMetadataForObject(NothingKClassImpl, 'NothingKClassImpl');
initMetadataForClass(SimpleKClassImpl, 'SimpleKClassImpl');
initMetadataForInterface(KProperty1, 'KProperty1');
initMetadataForObject(PrimitiveClasses, 'PrimitiveClasses');
initMetadataForClass(CharacterCodingException, 'CharacterCodingException', CharacterCodingException.xb);
initMetadataForClass(StringBuilder, 'StringBuilder', StringBuilder.d, VOID, [CharSequence]);
initMetadataForClass(sam$kotlin_Comparator$0, 'sam$kotlin_Comparator$0', VOID, VOID, [Comparator, FunctionAdapter]);
initMetadataForCompanion(Companion_4);
initMetadataForClass(AbstractMap$keys$1$iterator$1);
initMetadataForClass(AbstractMap$values$1$iterator$1);
initMetadataForCompanion(Companion_5);
initMetadataForClass(AbstractSet, 'AbstractSet', VOID, VOID, [KtSet]);
initMetadataForClass(AbstractMap$keys$1);
initMetadataForClass(AbstractMap$values$1);
initMetadataForCompanion(Companion_6);
protoOf(EmptyList).asJsReadonlyArrayView = asJsReadonlyArrayView;
initMetadataForObject(EmptyList, 'EmptyList', VOID, VOID, [KtList]);
initMetadataForObject(EmptyIterator, 'EmptyIterator');
initMetadataForClass(IndexedValue, 'IndexedValue');
initMetadataForClass(IndexingIterable, 'IndexingIterable');
initMetadataForClass(IndexingIterator, 'IndexingIterator');
protoOf(EmptyMap).asJsReadonlyMapView = asJsReadonlyMapView;
initMetadataForObject(EmptyMap, 'EmptyMap', VOID, VOID, [KtMap]);
initMetadataForClass(IntIterator, 'IntIterator');
initMetadataForObject(EmptySet, 'EmptySet', VOID, VOID, [KtSet]);
initMetadataForObject(Key, 'Key');
initMetadataForInterface(ContinuationInterceptor, 'ContinuationInterceptor');
initMetadataForObject(EmptyCoroutineContext, 'EmptyCoroutineContext');
initMetadataForClass(CoroutineSingletons, 'CoroutineSingletons');
initMetadataForCompanion(Companion_7);
initMetadataForClass(IntProgression, 'IntProgression');
initMetadataForClass(IntRange, 'IntRange');
initMetadataForClass(IntProgressionIterator, 'IntProgressionIterator');
initMetadataForCompanion(Companion_8);
initMetadataForObject(System, 'System');
initMetadataForCompanion(Companion_9);
initMetadataForClass(Instant, 'Instant');
initMetadataForCompanion(Companion_10);
initMetadataForClass(UnboundLocalDateTime, 'UnboundLocalDateTime');
initMetadataForClass(LazyThreadSafetyMode, 'LazyThreadSafetyMode');
initMetadataForClass(UnsafeLazyImpl, 'UnsafeLazyImpl');
initMetadataForObject(UNINITIALIZED_VALUE, 'UNINITIALIZED_VALUE');
initMetadataForCompanion(Companion_11);
initMetadataForClass(Failure, 'Failure');
initMetadataForClass(NotImplementedError, 'NotImplementedError', NotImplementedError.n9);
initMetadataForClass(Pair, 'Pair');
initMetadataForCompanion(Companion_12);
initMetadataForClass(Uuid, 'Uuid');
initMetadataForInterface(KSerializer, 'KSerializer');
initMetadataForClass(AbstractPolymorphicSerializer, 'AbstractPolymorphicSerializer', VOID, VOID, [KSerializer]);
initMetadataForClass(PolymorphicSerializer, 'PolymorphicSerializer');
initMetadataForClass(SerializationException, 'SerializationException', SerializationException.zf);
initMetadataForClass(MissingFieldException, 'MissingFieldException');
initMetadataForInterface(SerialDescriptor, 'SerialDescriptor');
initMetadataForClass(ContextDescriptor, 'ContextDescriptor', VOID, VOID, [SerialDescriptor]);
initMetadataForClass(elementDescriptors$1);
initMetadataForClass(elementDescriptors$$inlined$Iterable$1);
initMetadataForClass(ClassSerialDescriptorBuilder, 'ClassSerialDescriptorBuilder');
initMetadataForInterface(CachedNames, 'CachedNames');
protoOf(SerialDescriptorImpl).ag = get_isNullable;
initMetadataForClass(SerialDescriptorImpl, 'SerialDescriptorImpl', VOID, VOID, [SerialDescriptor, CachedNames]);
initMetadataForClass(SerialKind, 'SerialKind');
initMetadataForClass(PrimitiveKind, 'PrimitiveKind');
initMetadataForObject(BOOLEAN, 'BOOLEAN');
initMetadataForObject(INT, 'INT');
initMetadataForObject(LONG, 'LONG');
initMetadataForObject(STRING, 'STRING');
initMetadataForClass(PolymorphicKind, 'PolymorphicKind');
initMetadataForObject(OPEN, 'OPEN');
initMetadataForObject(CONTEXTUAL, 'CONTEXTUAL');
initMetadataForClass(StructureKind, 'StructureKind');
initMetadataForObject(CLASS, 'CLASS');
initMetadataForObject(LIST, 'LIST');
initMetadataForObject(MAP, 'MAP');
protoOf(ListLikeDescriptor).ag = get_isNullable;
initMetadataForClass(ListLikeDescriptor, 'ListLikeDescriptor', VOID, VOID, [SerialDescriptor]);
initMetadataForClass(ArrayListClassDesc, 'ArrayListClassDesc');
protoOf(MapLikeDescriptor).ag = get_isNullable;
initMetadataForClass(MapLikeDescriptor, 'MapLikeDescriptor', VOID, VOID, [SerialDescriptor]);
initMetadataForClass(LinkedHashMapClassDesc, 'LinkedHashMapClassDesc');
initMetadataForClass(AbstractCollectionSerializer, 'AbstractCollectionSerializer', VOID, VOID, [KSerializer]);
initMetadataForClass(CollectionLikeSerializer, 'CollectionLikeSerializer');
initMetadataForClass(CollectionSerializer, 'CollectionSerializer');
initMetadataForClass(ArrayListSerializer, 'ArrayListSerializer');
initMetadataForClass(MapLikeSerializer, 'MapLikeSerializer');
initMetadataForClass(LinkedHashMapSerializer, 'LinkedHashMapSerializer');
initMetadataForClass(NullableSerializer, 'NullableSerializer', VOID, VOID, [KSerializer]);
initMetadataForClass(SerialDescriptorForNullable, 'SerialDescriptorForNullable', VOID, VOID, [SerialDescriptor, CachedNames]);
protoOf(PluginGeneratedSerialDescriptor).ag = get_isNullable;
initMetadataForClass(PluginGeneratedSerialDescriptor, 'PluginGeneratedSerialDescriptor', VOID, VOID, [SerialDescriptor, CachedNames]);
initMetadataForInterface(GeneratedSerializer, 'GeneratedSerializer', VOID, VOID, [KSerializer]);
initMetadataForObject(StringSerializer, 'StringSerializer', VOID, VOID, [KSerializer]);
initMetadataForObject(IntSerializer, 'IntSerializer', VOID, VOID, [KSerializer]);
protoOf(PrimitiveSerialDescriptor).ag = get_isNullable;
initMetadataForClass(PrimitiveSerialDescriptor, 'PrimitiveSerialDescriptor', VOID, VOID, [SerialDescriptor]);
initMetadataForObject(LongSerializer, 'LongSerializer', VOID, VOID, [KSerializer]);
initMetadataForObject(BooleanSerializer, 'BooleanSerializer', VOID, VOID, [KSerializer]);
initMetadataForClass(SerializableWith, 'SerializableWith', VOID, VOID, VOID, VOID, 0);
initMetadataForInterface(Message, 'Message');
initMetadataForInterface(Command, 'Command', VOID, VOID, [Message]);
initMetadataForInterface(Event, 'Event', VOID, VOID, [Message]);
initMetadataForInterface(Problem, 'Problem', VOID, VOID, [Message]);
initMetadataForInterface(Query, 'Query', VOID, VOID, [Message]);
initMetadataForInterface(WithEnvelopeId, 'WithEnvelopeId');
initMetadataForInterface(WithEnvelopeData, 'WithEnvelopeData');
initMetadataForInterface(EnvelopeDTO, 'EnvelopeDTO', VOID, VOID, [WithEnvelopeId, WithEnvelopeData, Message]);
initMetadataForCompanion(Companion_13);
initMetadataForClass(Envelope, 'Envelope', VOID, VOID, [EnvelopeDTO], VOID, VOID, {0: Companion_getInstance_13});
initMetadataForInterface(F2ErrorDTO, 'F2ErrorDTO', VOID, VOID, [Problem]);
initMetadataForCompanion(Companion_14);
protoOf($serializer).ii = typeParametersSerializers;
initMetadataForObject($serializer, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(F2Error, 'F2Error', VOID, VOID, [F2ErrorDTO], VOID, VOID, {0: $serializer_getInstance});
initMetadataForCompanion(Companion_15);
initMetadataForClass(F2Exception, 'F2Exception');
initMetadataForInterface(Match, 'Match');
protoOf(AndMatch).or = or;
initMetadataForClass(AndMatch, 'AndMatch', VOID, VOID, [Match]);
protoOf(OrMatch).and = and;
initMetadataForClass(OrMatch, 'OrMatch', VOID, VOID, [Match]);
initMetadataForInterface(SortDTO, 'SortDTO');
initMetadataForInterface(PageDTO, 'PageDTO');
initMetadataForCompanion(Companion_16);
initMetadataForClass(Page, 'Page', VOID, VOID, [PageDTO], VOID, VOID, {0: Companion_getInstance_16});
initMetadataForInterface(PageQueryDTO, 'PageQueryDTO', VOID, VOID, [Query]);
initMetadataForInterface(PageQueryResultDTO, 'PageQueryResultDTO', VOID, VOID, [Event, PageDTO]);
initMetadataForCompanion(Companion_17);
protoOf($serializer_0).ii = typeParametersSerializers;
initMetadataForObject($serializer_0, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(PageQuery, 'PageQuery', VOID, VOID, [PageQueryDTO], VOID, VOID, {0: $serializer_getInstance_0});
initMetadataForCompanion(Companion_18);
initMetadataForClass(PageQueryResult, 'PageQueryResult', VOID, VOID, [PageQueryResultDTO], VOID, VOID, {0: Companion_getInstance_18});
initMetadataForCompanion(Companion_19);
initMetadataForInterface(Pagination, 'Pagination', VOID, VOID, VOID, VOID, VOID, {0: Companion_getInstance_19});
initMetadataForInterface(OffsetPaginationDTO, 'OffsetPaginationDTO', VOID, VOID, [Pagination]);
initMetadataForInterface(PagePaginationDTO, 'PagePaginationDTO', VOID, VOID, [Pagination]);
initMetadataForCompanion(Companion_20);
protoOf($serializer_1).ii = typeParametersSerializers;
initMetadataForObject($serializer_1, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OffsetPagination, 'OffsetPagination', VOID, VOID, [OffsetPaginationDTO], VOID, VOID, {0: $serializer_getInstance_1});
initMetadataForCompanion(Companion_21);
protoOf($serializer_2).ii = typeParametersSerializers;
initMetadataForObject($serializer_2, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(PagePagination, 'PagePagination', VOID, VOID, [PagePaginationDTO], VOID, VOID, {0: $serializer_getInstance_2});
initMetadataForInterface(OrganizationCoreDeleteCommandDTO, 'OrganizationCoreDeleteCommandDTO', VOID, VOID, [Command]);
initMetadataForCompanion(Companion_22);
protoOf($serializer_3).ii = typeParametersSerializers;
initMetadataForObject($serializer_3, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationCoreDeleteCommand, 'OrganizationCoreDeleteCommand', VOID, VOID, [OrganizationCoreDeleteCommandDTO], VOID, VOID, {0: $serializer_getInstance_3});
initMetadataForInterface(OrganizationCoreDeletedEventDTO, 'OrganizationCoreDeletedEventDTO', VOID, VOID, [Event]);
initMetadataForCompanion(Companion_23);
protoOf($serializer_4).ii = typeParametersSerializers;
initMetadataForObject($serializer_4, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationCoreDeletedEvent, 'OrganizationCoreDeletedEvent', VOID, VOID, [OrganizationCoreDeletedEventDTO], VOID, VOID, {0: $serializer_getInstance_4});
initMetadataForInterface(AuthedUserDTO, 'AuthedUserDTO');
initMetadataForClass(ImPermission, 'ImPermission');
initMetadataForObject(ExceptionCodes, 'ExceptionCodes');
initMetadataForInterface(AddressDTO, 'AddressDTO');
initMetadataForCompanion(Companion_24);
protoOf($serializer_5).ii = typeParametersSerializers;
initMetadataForObject($serializer_5, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(Address, 'Address', VOID, VOID, [AddressDTO], VOID, VOID, {0: $serializer_getInstance_5});
initMetadataForObject(PrivilegePolicies, 'PrivilegePolicies');
initMetadataForInterface(FeatureDefineCommandDTO, 'FeatureDefineCommandDTO');
initMetadataForCompanion(Companion_25);
protoOf($serializer_6).ii = typeParametersSerializers;
initMetadataForObject($serializer_6, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(FeatureDefineCommand, 'FeatureDefineCommand', VOID, VOID, [FeatureDefineCommandDTO], VOID, VOID, {0: $serializer_getInstance_6});
initMetadataForInterface(FeatureDefinedEventDTO, 'FeatureDefinedEventDTO', VOID, VOID, [Event]);
initMetadataForCompanion(Companion_26);
protoOf($serializer_7).ii = typeParametersSerializers;
initMetadataForObject($serializer_7, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(FeatureDefinedEvent, 'FeatureDefinedEvent', VOID, VOID, [FeatureDefinedEventDTO], VOID, VOID, {0: $serializer_getInstance_7});
initMetadataForInterface(PrivilegeDTO, 'PrivilegeDTO');
initMetadataForInterface(FeatureDTO, 'FeatureDTO', VOID, VOID, [PrivilegeDTO]);
initMetadataForCompanion(Companion_27);
protoOf($serializer_8).ii = typeParametersSerializers;
initMetadataForObject($serializer_8, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(Feature, 'Feature', VOID, VOID, [FeatureDTO], VOID, VOID, {0: $serializer_getInstance_8});
initMetadataForInterface(FeatureGetQueryDTO, 'FeatureGetQueryDTO');
initMetadataForCompanion(Companion_28);
protoOf($serializer_9).ii = typeParametersSerializers;
initMetadataForObject($serializer_9, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(FeatureGetQuery, 'FeatureGetQuery', VOID, VOID, [FeatureGetQueryDTO], VOID, VOID, {0: $serializer_getInstance_9});
initMetadataForInterface(FeatureGetResultDTO, 'FeatureGetResultDTO');
initMetadataForCompanion(Companion_29);
protoOf($serializer_10).ii = typeParametersSerializers;
initMetadataForObject($serializer_10, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(FeatureGetResult, 'FeatureGetResult', VOID, VOID, [FeatureGetResultDTO], VOID, VOID, {0: $serializer_getInstance_10});
initMetadataForInterface(FeatureListQueryDTO, 'FeatureListQueryDTO');
initMetadataForCompanion(Companion_30);
protoOf($serializer_11).ii = typeParametersSerializers;
initMetadataForObject($serializer_11, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(FeatureListQuery, 'FeatureListQuery', FeatureListQuery, VOID, [FeatureListQueryDTO], VOID, VOID, {0: $serializer_getInstance_11});
initMetadataForInterface(FeatureListResultDTO, 'FeatureListResultDTO');
initMetadataForCompanion(Companion_31);
protoOf($serializer_12).ii = typeParametersSerializers;
initMetadataForObject($serializer_12, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(FeatureListResult, 'FeatureListResult', VOID, VOID, [FeatureListResultDTO], VOID, VOID, {0: $serializer_getInstance_12});
initMetadataForInterface(PermissionDefineCommandDTO, 'PermissionDefineCommandDTO');
initMetadataForCompanion(Companion_32);
protoOf($serializer_13).ii = typeParametersSerializers;
initMetadataForObject($serializer_13, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(PermissionDefineCommand, 'PermissionDefineCommand', VOID, VOID, [PermissionDefineCommandDTO], VOID, VOID, {0: $serializer_getInstance_13});
initMetadataForInterface(PermissionDefinedEventDTO, 'PermissionDefinedEventDTO', VOID, VOID, [Event]);
initMetadataForCompanion(Companion_33);
protoOf($serializer_14).ii = typeParametersSerializers;
initMetadataForObject($serializer_14, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(PermissionDefinedEvent, 'PermissionDefinedEvent', VOID, VOID, [PermissionDefinedEventDTO], VOID, VOID, {0: $serializer_getInstance_14});
initMetadataForInterface(PermissionDTO, 'PermissionDTO', VOID, VOID, [PrivilegeDTO]);
initMetadataForCompanion(Companion_34);
protoOf($serializer_15).ii = typeParametersSerializers;
initMetadataForObject($serializer_15, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(Permission, 'Permission', VOID, VOID, [PermissionDTO], VOID, VOID, {0: $serializer_getInstance_15});
initMetadataForInterface(PermissionGetQueryDTO, 'PermissionGetQueryDTO');
initMetadataForCompanion(Companion_35);
protoOf($serializer_16).ii = typeParametersSerializers;
initMetadataForObject($serializer_16, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(PermissionGetQuery, 'PermissionGetQuery', VOID, VOID, [PermissionGetQueryDTO], VOID, VOID, {0: $serializer_getInstance_16});
initMetadataForInterface(PermissionGetResultDTO, 'PermissionGetResultDTO');
initMetadataForCompanion(Companion_36);
protoOf($serializer_17).ii = typeParametersSerializers;
initMetadataForObject($serializer_17, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(PermissionGetResult, 'PermissionGetResult', VOID, VOID, [PermissionGetResultDTO], VOID, VOID, {0: $serializer_getInstance_17});
initMetadataForInterface(PermissionListQueryDTO, 'PermissionListQueryDTO');
initMetadataForCompanion(Companion_37);
protoOf($serializer_18).ii = typeParametersSerializers;
initMetadataForObject($serializer_18, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(PermissionListQuery, 'PermissionListQuery', PermissionListQuery, VOID, [PermissionListQueryDTO], VOID, VOID, {0: $serializer_getInstance_18});
initMetadataForInterface(PermissionListResultDTO, 'PermissionListResultDTO');
initMetadataForCompanion(Companion_38);
protoOf($serializer_19).ii = typeParametersSerializers;
initMetadataForObject($serializer_19, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(PermissionListResult, 'PermissionListResult', VOID, VOID, [PermissionListResultDTO], VOID, VOID, {0: $serializer_getInstance_19});
initMetadataForInterface(RoleDefineCommandDTO, 'RoleDefineCommandDTO');
initMetadataForCompanion(Companion_39);
protoOf($serializer_20).ii = typeParametersSerializers;
initMetadataForObject($serializer_20, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(RoleDefineCommand, 'RoleDefineCommand', VOID, VOID, [RoleDefineCommandDTO], VOID, VOID, {0: $serializer_getInstance_20});
initMetadataForInterface(RoleDefinedEventDTO, 'RoleDefinedEventDTO', VOID, VOID, [Event]);
initMetadataForCompanion(Companion_40);
protoOf($serializer_21).ii = typeParametersSerializers;
initMetadataForObject($serializer_21, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(RoleDefinedEvent, 'RoleDefinedEvent', VOID, VOID, [RoleDefinedEventDTO], VOID, VOID, {0: $serializer_getInstance_21});
initMetadataForInterface(RoleDTO, 'RoleDTO', VOID, VOID, [PrivilegeDTO]);
initMetadataForCompanion(Companion_41);
protoOf($serializer_22).ii = typeParametersSerializers;
initMetadataForObject($serializer_22, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(Role, 'Role', VOID, VOID, [RoleDTO], VOID, VOID, {0: $serializer_getInstance_22});
initMetadataForObject(RoleTargetValues, 'RoleTargetValues');
initMetadataForInterface(RoleGetQueryDTO, 'RoleGetQueryDTO');
initMetadataForCompanion(Companion_42);
protoOf($serializer_23).ii = typeParametersSerializers;
initMetadataForObject($serializer_23, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(RoleGetQuery, 'RoleGetQuery', VOID, VOID, [RoleGetQueryDTO], VOID, VOID, {0: $serializer_getInstance_23});
initMetadataForInterface(RoleGetResultDTO, 'RoleGetResultDTO');
initMetadataForCompanion(Companion_43);
protoOf($serializer_24).ii = typeParametersSerializers;
initMetadataForObject($serializer_24, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(RoleGetResult, 'RoleGetResult', VOID, VOID, [RoleGetResultDTO], VOID, VOID, {0: $serializer_getInstance_24});
initMetadataForInterface(RoleListQueryDTO, 'RoleListQueryDTO');
initMetadataForCompanion(Companion_44);
protoOf($serializer_25).ii = typeParametersSerializers;
initMetadataForObject($serializer_25, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(RoleListQuery, 'RoleListQuery', VOID, VOID, [RoleListQueryDTO], VOID, VOID, {0: $serializer_getInstance_25});
initMetadataForInterface(RoleListResultDTO, 'RoleListResultDTO');
initMetadataForCompanion(Companion_45);
protoOf($serializer_26).ii = typeParametersSerializers;
initMetadataForObject($serializer_26, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(RoleListResult, 'RoleListResult', VOID, VOID, [RoleListResultDTO], VOID, VOID, {0: $serializer_getInstance_26});
initMetadataForClass(asFlow$$inlined$unsafeFlow$1, VOID, VOID, VOID, VOID, [1]);
initMetadataForInterface(FlowCollector, 'FlowCollector', VOID, VOID, VOID, [1]);
initMetadataForClass(sam$kotlinx_coroutines_flow_FlowCollector$0, 'sam$kotlinx_coroutines_flow_FlowCollector$0', VOID, VOID, [FlowCollector, FunctionAdapter], [1]);
initMetadataForInterface(F2Consumer, 'F2Consumer', VOID, VOID, VOID, [1]);
initMetadataForInterface(F2Function, 'F2Function', VOID, VOID, VOID, [1]);
initMetadataForInterface(F2Supplier, 'F2Supplier', VOID, VOID, VOID, [0]);
initMetadataForInterface(F2SupplierSingle, 'F2SupplierSingle', VOID, VOID, VOID, [0]);
initMetadataForCompanion(Companion_46);
protoOf($serializer_27).ii = typeParametersSerializers;
initMetadataForObject($serializer_27, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(Batch, 'Batch', Batch, VOID, VOID, VOID, VOID, {0: $serializer_getInstance_27});
initMetadataForClass(AuthRealm, 'AuthRealm');
initMetadataForClass(AuthRealmPassword, 'AuthRealmPassword');
initMetadataForClass(AuthRealmClientSecret, 'AuthRealmClientSecret');
initMetadataForCompanion(Companion_47);
protoOf($serializer_28).ii = typeParametersSerializers;
initMetadataForObject($serializer_28, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(TokenInfo, 'TokenInfo', VOID, VOID, VOID, VOID, VOID, {0: $serializer_getInstance_28});
initMetadataForInterface(OrganizationCreateCommandDTO, 'OrganizationCreateCommandDTO', VOID, VOID, [Command]);
initMetadataForCompanion(Companion_48);
protoOf($serializer_29).ii = typeParametersSerializers;
initMetadataForObject($serializer_29, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationCreateCommand, 'OrganizationCreateCommand', VOID, VOID, [OrganizationCreateCommandDTO], VOID, VOID, {0: $serializer_getInstance_29});
initMetadataForInterface(OrganizationCreatedEventDTO, 'OrganizationCreatedEventDTO', VOID, VOID, [Event]);
initMetadataForCompanion(Companion_49);
protoOf($serializer_30).ii = typeParametersSerializers;
initMetadataForObject($serializer_30, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationCreatedEvent, 'OrganizationCreatedEvent', VOID, VOID, [OrganizationCreatedEventDTO], VOID, VOID, {0: $serializer_getInstance_30});
initMetadataForInterface(OrganizationDeleteCommandDTO, 'OrganizationDeleteCommandDTO', VOID, VOID, [OrganizationCoreDeleteCommandDTO]);
initMetadataForInterface(OrganizationDeletedEventDTO, 'OrganizationDeletedEventDTO', VOID, VOID, [OrganizationCoreDeletedEventDTO]);
initMetadataForInterface(OrganizationDisableCommandDTO, 'OrganizationDisableCommandDTO', VOID, VOID, [Command]);
initMetadataForCompanion(Companion_50);
protoOf($serializer_31).ii = typeParametersSerializers;
initMetadataForObject($serializer_31, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationDisableCommand, 'OrganizationDisableCommand', VOID, VOID, [OrganizationDisableCommandDTO], VOID, VOID, {0: $serializer_getInstance_31});
initMetadataForInterface(OrganizationDisabledEventDTO, 'OrganizationDisabledEventDTO', VOID, VOID, [Event]);
initMetadataForCompanion(Companion_51);
protoOf($serializer_32).ii = typeParametersSerializers;
initMetadataForObject($serializer_32, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationDisabledEvent, 'OrganizationDisabledEvent', VOID, VOID, [OrganizationDisabledEventDTO], VOID, VOID, {0: $serializer_getInstance_32});
initMetadataForInterface(OrganizationUpdateCommandDTO, 'OrganizationUpdateCommandDTO', VOID, VOID, [Command]);
initMetadataForCompanion(Companion_52);
protoOf($serializer_33).ii = typeParametersSerializers;
initMetadataForObject($serializer_33, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationUpdateCommand, 'OrganizationUpdateCommand', VOID, VOID, [OrganizationUpdateCommandDTO], VOID, VOID, {0: $serializer_getInstance_33});
initMetadataForInterface(OrganizationUpdatedResultDTO, 'OrganizationUpdatedResultDTO', VOID, VOID, [Event]);
initMetadataForCompanion(Companion_53);
protoOf($serializer_34).ii = typeParametersSerializers;
initMetadataForObject($serializer_34, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationUpdatedResult, 'OrganizationUpdatedResult', VOID, VOID, [OrganizationUpdatedResultDTO], VOID, VOID, {0: $serializer_getInstance_34});
initMetadataForInterface(OrganizationUploadLogoCommandDTO, 'OrganizationUploadLogoCommandDTO', VOID, VOID, [Command]);
initMetadataForInterface(OrganizationUploadedLogoEventDTO, 'OrganizationUploadedLogoEventDTO', VOID, VOID, [Event]);
initMetadataForInterface(OrganizationDTO, 'OrganizationDTO');
initMetadataForCompanion(Companion_54);
protoOf($serializer_35).ii = typeParametersSerializers;
initMetadataForObject($serializer_35, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(Organization, 'Organization', VOID, VOID, [OrganizationDTO], VOID, VOID, {0: $serializer_getInstance_35});
initMetadataForInterface(OrganizationRefDTO, 'OrganizationRefDTO');
initMetadataForCompanion(Companion_55);
protoOf($serializer_36).ii = typeParametersSerializers;
initMetadataForObject($serializer_36, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationRef, 'OrganizationRef', VOID, VOID, [OrganizationRefDTO], VOID, VOID, {0: $serializer_getInstance_36});
initMetadataForObject(OrganizationStatusValues, 'OrganizationStatusValues');
initMetadataForObject(OrganizationPolicies, 'OrganizationPolicies');
initMetadataForInterface(OrganizationGetFromInseeQueryDTO, 'OrganizationGetFromInseeQueryDTO', VOID, VOID, [Query]);
initMetadataForCompanion(Companion_56);
protoOf($serializer_37).ii = typeParametersSerializers;
initMetadataForObject($serializer_37, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationGetFromInseeQuery, 'OrganizationGetFromInseeQuery', VOID, VOID, [OrganizationGetFromInseeQueryDTO], VOID, VOID, {0: $serializer_getInstance_37});
initMetadataForInterface(OrganizationGetFromInseeResultDTO, 'OrganizationGetFromInseeResultDTO', VOID, VOID, [Event]);
initMetadataForCompanion(Companion_57);
protoOf($serializer_38).ii = typeParametersSerializers;
initMetadataForObject($serializer_38, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationGetFromInseeResult, 'OrganizationGetFromInseeResult', VOID, VOID, [OrganizationGetFromInseeResultDTO], VOID, VOID, {0: $serializer_getInstance_38});
initMetadataForInterface(OrganizationGetQueryDTO, 'OrganizationGetQueryDTO', VOID, VOID, [Query]);
initMetadataForCompanion(Companion_58);
protoOf($serializer_39).ii = typeParametersSerializers;
initMetadataForObject($serializer_39, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationGetQuery, 'OrganizationGetQuery', VOID, VOID, [OrganizationGetQueryDTO], VOID, VOID, {0: $serializer_getInstance_39});
initMetadataForInterface(OrganizationGetResultDTO, 'OrganizationGetResultDTO', VOID, VOID, [Event]);
initMetadataForCompanion(Companion_59);
protoOf($serializer_40).ii = typeParametersSerializers;
initMetadataForObject($serializer_40, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationGetResult, 'OrganizationGetResult', VOID, VOID, [OrganizationGetResultDTO], VOID, VOID, {0: $serializer_getInstance_40});
initMetadataForInterface(OrganizationPageQueryDTO, 'OrganizationPageQueryDTO', VOID, VOID, [Query]);
initMetadataForCompanion(Companion_60);
protoOf($serializer_41).ii = typeParametersSerializers;
initMetadataForObject($serializer_41, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationPageQuery, 'OrganizationPageQuery', VOID, VOID, [OrganizationPageQueryDTO], VOID, VOID, {0: $serializer_getInstance_41});
initMetadataForInterface(OrganizationPageResultDTO, 'OrganizationPageResultDTO', VOID, VOID, [PageDTO]);
initMetadataForCompanion(Companion_61);
protoOf($serializer_42).ii = typeParametersSerializers;
initMetadataForObject($serializer_42, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationPageResult, 'OrganizationPageResult', VOID, VOID, [OrganizationPageResultDTO], VOID, VOID, {0: $serializer_getInstance_42});
initMetadataForInterface(OrganizationRefGetQueryDTO, 'OrganizationRefGetQueryDTO', VOID, VOID, [Query]);
initMetadataForCompanion(Companion_62);
protoOf($serializer_43).ii = typeParametersSerializers;
initMetadataForObject($serializer_43, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationRefGetQuery, 'OrganizationRefGetQuery', VOID, VOID, [OrganizationRefGetQueryDTO], VOID, VOID, {0: $serializer_getInstance_43});
initMetadataForInterface(OrganizationRefGetResultDTO, 'OrganizationRefGetResultDTO', VOID, VOID, [Event]);
initMetadataForCompanion(Companion_63);
protoOf($serializer_44).ii = typeParametersSerializers;
initMetadataForObject($serializer_44, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationRefGetResult, 'OrganizationRefGetResult', VOID, VOID, [OrganizationRefGetResultDTO], VOID, VOID, {0: $serializer_getInstance_44});
initMetadataForInterface(OrganizationRefListQueryDTO, 'OrganizationRefListQueryDTO', VOID, VOID, [Query]);
initMetadataForCompanion(Companion_64);
protoOf($serializer_45).ii = typeParametersSerializers;
initMetadataForObject($serializer_45, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationRefListQuery, 'OrganizationRefListQuery', OrganizationRefListQuery, VOID, [OrganizationRefListQueryDTO], VOID, VOID, {0: $serializer_getInstance_45});
initMetadataForInterface(OrganizationRefListResultDTO, 'OrganizationRefListResultDTO', VOID, VOID, [Event]);
initMetadataForCompanion(Companion_65);
protoOf($serializer_46).ii = typeParametersSerializers;
initMetadataForObject($serializer_46, '$serializer', VOID, VOID, [GeneratedSerializer]);
initMetadataForClass(OrganizationRefListResult, 'OrganizationRefListResult', VOID, VOID, [OrganizationRefListResultDTO], VOID, VOID, {0: $serializer_getInstance_46});
//endregion
//region block: init
Companion_instance = new Companion();
Companion_instance_0 = new Companion_0();
Companion_instance_1 = new Companion_1();
StringCompanionObject_instance = new StringCompanionObject();
Unit_instance = new Unit();
Companion_instance_3 = new Companion_3();
CompletedContinuation_instance = new CompletedContinuation();
Companion_instance_4 = new Companion_4();
Companion_instance_5 = new Companion_5();
Companion_instance_6 = new Companion_6();
EmptyList_instance = new EmptyList();
EmptyIterator_instance = new EmptyIterator();
EmptyMap_instance = new EmptyMap();
EmptySet_instance = new EmptySet();
Key_instance = new Key();
EmptyCoroutineContext_instance = new EmptyCoroutineContext();
Companion_instance_8 = new Companion_8();
System_instance = new System();
Companion_instance_10 = new Companion_10();
UNINITIALIZED_VALUE_instance = new UNINITIALIZED_VALUE();
Companion_instance_11 = new Companion_11();
Companion_instance_14 = new Companion_14();
Companion_instance_15 = new Companion_15();
Companion_instance_19 = new Companion_19();
Companion_instance_20 = new Companion_20();
Companion_instance_21 = new Companion_21();
Companion_instance_22 = new Companion_22();
Companion_instance_23 = new Companion_23();
ExceptionCodes_instance = new ExceptionCodes();
Companion_instance_24 = new Companion_24();
PrivilegePolicies_instance = new PrivilegePolicies();
Companion_instance_25 = new Companion_25();
Companion_instance_26 = new Companion_26();
Companion_instance_27 = new Companion_27();
Companion_instance_28 = new Companion_28();
Companion_instance_29 = new Companion_29();
Companion_instance_30 = new Companion_30();
Companion_instance_33 = new Companion_33();
Companion_instance_35 = new Companion_35();
Companion_instance_36 = new Companion_36();
Companion_instance_37 = new Companion_37();
Companion_instance_40 = new Companion_40();
RoleTargetValues_instance = new RoleTargetValues();
Companion_instance_42 = new Companion_42();
Companion_instance_43 = new Companion_43();
Companion_instance_44 = new Companion_44();
Companion_instance_46 = new Companion_46();
Companion_instance_47 = new Companion_47();
Companion_instance_49 = new Companion_49();
Companion_instance_53 = new Companion_53();
OrganizationStatusValues_instance = new OrganizationStatusValues();
OrganizationPolicies_instance = new OrganizationPolicies();
Companion_instance_56 = new Companion_56();
Companion_instance_57 = new Companion_57();
Companion_instance_58 = new Companion_58();
Companion_instance_59 = new Companion_59();
Companion_instance_62 = new Companion_62();
Companion_instance_63 = new Companion_63();
Companion_instance_64 = new Companion_64();
//endregion
//region block: exports
var KtList_0 = {};
KtList_0.fromJsArray = fromJsArray;
var KtMap_0 = {};
KtMap_0.fromJsMap = fromJsMap;
defineProp(F2Exception, 'Companion', Companion_getInstance_15, VOID, true);
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
PrivilegePolicies.getInstance = PrivilegePolicies_getInstance;
RoleTargetValues.getInstance = RoleTargetValues_getInstance;
OrganizationStatusValues.getInstance = OrganizationStatusValues_getInstance;
OrganizationPolicies.getInstance = OrganizationPolicies_getInstance;
export {
  KtList_0 as KtList,
  KtMap_0 as KtMap,
  Envelope as Envelope,
  F2Error as F2Error,
  F2Exception as F2Exception,
  Page as Page,
  PageQuery as PageQuery,
  PageQueryResult as PageQueryResult,
  OffsetPagination as OffsetPagination,
  PagePagination as PagePagination,
  OrganizationCoreDeleteCommand as OrganizationCoreDeleteCommand,
  OrganizationCoreDeletedEvent as OrganizationCoreDeletedEvent,
  ImPermission as ImPermission,
  ExceptionCodes as ExceptionCodes,
  Address as Address,
  PrivilegePolicies as PrivilegePolicies,
  FeatureDefineCommand as FeatureDefineCommand,
  FeatureDefinedEvent as FeatureDefinedEvent,
  Feature as Feature,
  FeatureGetQuery as FeatureGetQuery,
  FeatureGetResult as FeatureGetResult,
  FeatureListQuery as FeatureListQuery,
  FeatureListResult as FeatureListResult,
  PermissionDefineCommand as PermissionDefineCommand,
  PermissionDefinedEvent as PermissionDefinedEvent,
  Permission as Permission,
  PermissionGetQuery as PermissionGetQuery,
  PermissionGetResult as PermissionGetResult,
  PermissionListQuery as PermissionListQuery,
  PermissionListResult as PermissionListResult,
  RoleDefineCommand as RoleDefineCommand,
  RoleDefinedEvent as RoleDefinedEvent,
  Role as Role,
  RoleTargetValues as RoleTargetValues,
  RoleGetQuery as RoleGetQuery,
  RoleGetResult as RoleGetResult,
  RoleListQuery as RoleListQuery,
  RoleListResult as RoleListResult,
  Batch as Batch,
  invokeF2Function as invokeF2Function,
  invokeF2Consumer as invokeF2Consumer,
  invokeF2Supplier as invokeF2Supplier,
  invokeF2SupplierSingle as invokeF2SupplierSingle,
  AuthRealm as AuthRealm,
  AuthRealmPassword as AuthRealmPassword,
  AuthRealmClientSecret as AuthRealmClientSecret,
  TokenInfo as TokenInfo,
  OrganizationCreateCommand as OrganizationCreateCommand,
  OrganizationCreatedEvent as OrganizationCreatedEvent,
  OrganizationDisableCommand as OrganizationDisableCommand,
  OrganizationDisabledEvent as OrganizationDisabledEvent,
  OrganizationUpdateCommand as OrganizationUpdateCommand,
  OrganizationUpdatedResult as OrganizationUpdatedResult,
  Organization as Organization,
  OrganizationRef as OrganizationRef,
  OrganizationStatusValues as OrganizationStatusValues,
  OrganizationPolicies as OrganizationPolicies,
  OrganizationGetFromInseeQuery as OrganizationGetFromInseeQuery,
  OrganizationGetFromInseeResult as OrganizationGetFromInseeResult,
  OrganizationGetQuery as OrganizationGetQuery,
  OrganizationGetResult as OrganizationGetResult,
  OrganizationPageQuery as OrganizationPageQuery,
  OrganizationPageResult as OrganizationPageResult,
  OrganizationRefGetQuery as OrganizationRefGetQuery,
  OrganizationRefGetResult as OrganizationRefGetResult,
  OrganizationRefListQuery as OrganizationRefListQuery,
  OrganizationRefListResult as OrganizationRefListResult,
};
//endregion

//# sourceMappingURL=connect-im-im-f2-organization-im-organization-domain.mjs.map
