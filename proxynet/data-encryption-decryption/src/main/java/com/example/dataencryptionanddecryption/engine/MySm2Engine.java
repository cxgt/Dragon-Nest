package com.example.dataencryptionanddecryption.engine;

import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.spec.ECParameterSpec;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 * SM2引擎类
 * @author chenxin
 * @date 2023/08/24 14:56
 */
public class MySm2Engine {
    public static final int Type_Encode = 0;
    public static final int Type_Decode = 1;

    /**
     * 创建一个SM2引擎
     * @param pubKey
     * @param priKey
     * @param enOrde
     * @return
     * @throws Exception
     */
    public static SM2Engine createMySm2Engine(PublicKey pubKey,PrivateKey priKey,int enOrde) throws Exception {
        if (enOrde == Type_Encode) {
            ECPublicKeyParameters ecPublicKeyParameters = null;
            if (pubKey instanceof BCECPublicKey) {
                BCECPublicKey bcPubKey = (BCECPublicKey) pubKey;
                ECParameterSpec ecParameterSpec = bcPubKey.getParameters();
                ECDomainParameters ecDomainParameters = new ECDomainParameters(ecParameterSpec.getCurve(),
                        ecParameterSpec.getG(), ecParameterSpec.getN());
                ecPublicKeyParameters = new ECPublicKeyParameters(bcPubKey.getQ(),ecDomainParameters);
            }
            SM2Engine sm2Engine = new SM2Engine();
            sm2Engine.init(true, new ParametersWithRandom(ecPublicKeyParameters, new SecureRandom()));
            return sm2Engine;
        }else {

            BCECPrivateKey bcecPrivateKey = (BCECPrivateKey) priKey;
            ECParameterSpec ecParameterSpec = bcecPrivateKey.getParameters();
            ECDomainParameters ecDomainParameters = new ECDomainParameters(ecParameterSpec.getCurve(),
                    ecParameterSpec.getG(), ecParameterSpec.getN());
            ECPrivateKeyParameters ecPrivateKeyParameters = new ECPrivateKeyParameters(bcecPrivateKey.getD(),
                    ecDomainParameters);
            SM2Engine sm2Engine = new SM2Engine();
            sm2Engine.init(false, ecPrivateKeyParameters);
            return sm2Engine;
        }
    }
}


