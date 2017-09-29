module HttpUtils exposing (..)

import Json.Encode as Encode
import Json.Decode as Decode
import Json.Decode.Pipeline exposing (decode, required, optional)
import Model exposing (..)


adReplacementSettingsDecoder : Decode.Decoder (List AdReplacementEntry)
adReplacementSettingsDecoder =
  Decode.at["adReplacementSettings"] (Decode.list adReplacementEntryDecoder)

adReplacementEntryDecoder : Decode.Decoder AdReplacementEntry
adReplacementEntryDecoder =
  decode AdReplacementEntry
    |> required "product" Decode.string
    |> required "isAdReplacementEnabled" Decode.bool
    |> required "adReplacementChannels" Decode.string
    |> required "isTokenRequired" Decode.bool

adReplacementEntryEncoder : AdReplacementEntry -> Encode.Value
adReplacementEntryEncoder entry =
    let
      attributes =
        [ ( "product", Encode.string entry.productName )
        , ( "isAdReplacementEnabled", Encode.bool entry.isAdReplacementEnabled )
        , ( "adReplacementChannels", Encode.string entry.adReplacementChannels )
        , ( "isTokenRequired", Encode.bool entry.isTokenRequired )
        ]
    in
      Encode.object attributes

entryListEncoder : AdReplacementEntry -> Encode.Value
entryListEncoder entry =
  Encode.list [adReplacementEntryEncoder entry]
