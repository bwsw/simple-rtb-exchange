package com.bitworks.rtb.model.request

/**
  * The most general type of impression, e.g. a simple static image, an expandable ad unit,
  * in-banner video.
  *
  * @param w        width of the impression in pixels. If neither wmin nor wmax are specified,
  *                 this value is an exact width requirement. Otherwise it is a preferred width.
  * @param h        height of the impression in pixels. If neither hmin nor hmax are specified,
  *                 this value is an exact height requirement. Otherwise it is a preferred height.
  * @param wmax     maximum width of the impression in pixels. If included along with a w value
  *                 then w should be interpreted as a recommended or preferred width.
  * @param hmax     maximum height of the impression in pixels. If included along with an h value
  *                 then h should be interpreted as a recommended or preferred height.
  * @param wmin     minimum width of the impression in pixels. If included along with a w value
  *                 then w should be interpreted as a recommended or preferred width.
  * @param hmin     minimum height of the impression in pixels. If included along with an h value
  *                 then h should be interpreted as a recommended or preferred height.
  * @param id       unique identifier for this banner object; should be unique within an impression
  * @param btype    blocked banner ad types
  * @param battr    blocked creative attributes
  * @param pos      ad position on screen
  * @param mimes    content MIME types supported
  * @param topFrame indicates if the banner is in the top frame as opposed to an iframe,
  *                 where 0 = no, 1 = yes
  * @param expdir   directions in which the banner may expand
  * @param api      list of supported API frameworks for this impression. If an API is
  *                 not explicitly listed, it is assumed not to be supported.
  * @param ext      placeholder for exchange-specific extensions to OpenRTB
  * @author Egor Ilchenko
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
    topFrame: Option[Int],
    expdir: Option[Seq[Int]],
    api: Option[Seq[Int]],
    ext: Option[Any])
