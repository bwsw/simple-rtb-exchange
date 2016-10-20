package com.bitworks.rtb.model.request

/** The most general type of impression.
  *
  * Although the term “banner” may have very specific meaning in other contexts,
  * here it can be many things including a simple static image, an expandable ad unit,
  * or even in-banner video (refer to the Video object in Section 3.2.4 for the more
  * generalized and full featured video ad units). An array of Banner objects can also
  * appear within the Video to describe optional companion ads defined in the VAST specification.
  *
  * The presence of a Banner as a subordinate of the Imp object indicates that this
  * impression is offered as a banner type impression. At the publisher’s discretion,
  * that same impression may also be offered as video and/or native by also including as Imp
  * subordinates the Video and/or Native objects, respectively. However, any given bid for
  * the impression must conform to one of the offered types.
  *
  * Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  *          All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  * @param w        Width of the impression in pixels. If neither wmin nor wmax are specified,
  *                 this value is an exact width requirement. Otherwise it is a preferred width.
  * @param h        Height of the impression in pixels. If neither hmin nor hmax are specified,
  *                 this value is an exact height requirement. Otherwise it is a preferred height.
  * @param wmax     Maximum width of the impression in pixels. If included along with a w value
  *                 then w should be interpreted as a recommended or preferred width.
  * @param hmax     Maximum height of the impression in pixels. If included along with an h value
  *                 then h should be interpreted as a recommended or preferred height.
  * @param wmin     Minimum width of the impression in pixels. If included along with a w value
  *                 then w should be interpreted as a recommended or preferred width.
  * @param hmin     Minimum height of the impression in pixels. If included along with an h value
  *                 then h should be interpreted as a recommended or preferred height.
  * @param id       Unique identifier for this banner object. Recommended when Banner objects are used
  *                 with a Video object to represent an array of companion ads. Values usually start at 1
  *                 and increase with each object; should be unique within an impression.
  * @param btype    Blocked banner ad types.
  * @param battr    Blocked creative attributes.
  * @param pos      Ad position on screen.
  * @param mimes    Content MIME types supported.
  * @param topframe Indicates if the banner is in the top frame as opposed to an
  *                 iframe, where 0 = no, 1 = yes
  * @param expdir   Directions in which the banner may expand.
  * @param api      List of supported API frameworks for this impression. If an API is
  *                 not explicitly listed, it is assumed not to be supported.
  * @param ext      Placeholder for exchange-specific extensions to OpenRTB.
  */
case class Banner(
    w: Option[Int],
    h: Option[Int],
    wmax: Option[Int],
    hmax: Option[Int],
    wmin: Option[Int],
    hmin: Option[Int],
    id: Option[String],
    btype: Option[Seq[Int]],
    battr: Option[Seq[Int]],
    pos: Option[Int],
    mimes: Option[Seq[String]],
    topframe: Option[Int],
    expdir: Option[Seq[Int]],
    api: Option[Seq[Int]],
    ext: Option[Any])
