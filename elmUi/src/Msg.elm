module Msg exposing (..)

import Http
import Model exposing (..)

type Msg
  = YospaceAdReplacementEnabled Bool
  | Channel String
  | TokenRequired Bool
  | NewProduct String
  | Update
  | UpdateChoices (Result Http.Error ())
  | Fetch (Result Http.Error (List AdReplacementEntry))
