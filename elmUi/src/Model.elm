module Model exposing (..)


type alias Model =
    { oldAdreplacementEntry : AdReplacementEntry,
      newAdreplacementEntry : AdReplacementEntry
    }


type alias AdReplacementEntry =
    { productName : String
    , isAdReplacementEnabled : Bool
    , adReplacementChannels : String
    , isTokenRequired : Bool
    }


createEntry : String -> Bool -> String -> Bool -> AdReplacementEntry
createEntry productName adEnabled channels token =
    { productName = productName
    , isAdReplacementEnabled = adEnabled
    , adReplacementChannels = channels
    , isTokenRequired = token
    }


initialModel : Model
initialModel =
    { oldAdreplacementEntry = createEntry "ios" True "" True,
      newAdreplacementEntry = createEntry "ios" True "" True
    }
