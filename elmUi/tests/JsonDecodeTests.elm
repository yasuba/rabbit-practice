module JsonDecodeTests exposing (..)

import Expect exposing (Expectation)
import Fuzz exposing (Fuzzer, list, int, string)
import Test exposing (..)
import Json.Decode as Decode
import Json.Encode as Encode
import Commands exposing (..)
import Models exposing (AdReplacementEntry)

suite : Test
suite =
  describe "AdReplacementEntry encoder and decoder"
    [ describe "The decoder"
      [ test "can successfully decode a Json string into an AdreplacementEntry" <|
        \() ->
          let
            input =
              """{"product": "dotcom","isAdReplacementEnabled": false,"adReplacementChannels": "itv2","isTokenRequired": false}"""
            decodedOutput =
              Decode.decodeString
                adReplacementEntryDecoder input
          in
            Expect.equal decodedOutput
              ( Ok
                { productName = "dotcom"
                , isAdReplacementEnabled = False
                , adReplacementChannels = "itv2"
                , isTokenRequired = False
                }
              )
        , test "returns an error if json string does not contain correct fields" <|
          \() ->
            let
              input =
                """{"product": "dotcom","isAdReplacementEnabled": "false","adReplacementChannels": "itv2","isTokenRequired": false}"""
              decodedOutput =
                Decode.decodeString
                  adReplacementEntryDecoder input
            in
              Expect.equal (success decodedOutput) False
      ]
      , describe "AdReplacementEntry encoder"
        [ test "can successfully encode an AdReplacementEntry into json" <|
          \() ->
            let
              input =
                { productName = "dotcom"
                , isAdReplacementEnabled = False
                , adReplacementChannels = "itv2"
                , isTokenRequired = False
                }
              encodedOutputAsString =
                   Encode.encode 0 (adReplacementEntryEncoder input)
            in
              Expect.equal encodedOutputAsString
                """{"product":"dotcom","isAdReplacementEnabled":false,"adReplacementChannels":"itv2","isTokenRequired":false}"""
        ]
    ]

success : Result a b -> Bool
success result =
  case result of
    Ok _ ->
        True
    Err _ ->
        False
