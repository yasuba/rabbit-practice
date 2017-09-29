module UpdateTests exposing (..)

import Expect exposing (Expectation)
import Fuzz exposing (Fuzzer, list, int, string)
import Test exposing (..)
import Models exposing (..)
import Commands exposing (..)

suite : Test
suite =
  describe "Update events"
    [ describe "Fetch"
      [ test "fetches data from magni DB and writes to adreplacementEntry" <|
        \() ->
          let
            model = initialModel
            entry = [
              { productName = "dotcom"
              , isAdReplacementEnabled = False
              , adReplacementChannels = "itv2"
              , isTokenRequired = False
              }
            ]
            updatedModel =
              fetchAdreplacementSettings model entry
          in
            Expect.equal updatedModel.adreplacementEntry.productName "dotcom"
      ]
    ]
