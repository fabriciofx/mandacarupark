/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2022 Fabrício Barros Cabral
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.fabriciofx.mandacarupark.crc;

/**
 * CRC-64 implementation with ability to combine checksums calculated over
 * different blocks of data.
 * <p>
 * https://stackoverflow.com/questions/20562546/how-to-get-crc64-distributed-calculation-use-its-linearity-property
 * https://stackoverflow.com/questions/5563502/uuid-to-unique-integer-id
 **/
public final class Crc64 implements Crc {
    /* CRC64 calculation table. */
    private final static long[] TABLE = {
        0x0000000000000000L, 0xb32e4cbe03a75f6fL, 0xf4843657a840a05bL,
        0x47aa7ae9abe7ff34L, 0x7bd0c384ff8f5e33L, 0xc8fe8f3afc28015cL,
        0x8f54f5d357cffe68L, 0x3c7ab96d5468a107L, 0xf7a18709ff1ebc66L,
        0x448fcbb7fcb9e309L, 0x0325b15e575e1c3dL, 0xb00bfde054f94352L,
        0x8c71448d0091e255L, 0x3f5f08330336bd3aL, 0x78f572daa8d1420eL,
        0xcbdb3e64ab761d61L, 0x7d9ba13851336649L, 0xceb5ed8652943926L,
        0x891f976ff973c612L, 0x3a31dbd1fad4997dL, 0x064b62bcaebc387aL,
        0xb5652e02ad1b6715L, 0xf2cf54eb06fc9821L, 0x41e11855055bc74eL,
        0x8a3a2631ae2dda2fL, 0x39146a8fad8a8540L, 0x7ebe1066066d7a74L,
        0xcd905cd805ca251bL, 0xf1eae5b551a2841cL, 0x42c4a90b5205db73L,
        0x056ed3e2f9e22447L, 0xb6409f5cfa457b28L, 0xfb374270a266cc92L,
        0x48190ecea1c193fdL, 0x0fb374270a266cc9L, 0xbc9d3899098133a6L,
        0x80e781f45de992a1L, 0x33c9cd4a5e4ecdceL, 0x7463b7a3f5a932faL,
        0xc74dfb1df60e6d95L, 0x0c96c5795d7870f4L, 0xbfb889c75edf2f9bL,
        0xf812f32ef538d0afL, 0x4b3cbf90f69f8fc0L, 0x774606fda2f72ec7L,
        0xc4684a43a15071a8L, 0x83c230aa0ab78e9cL, 0x30ec7c140910d1f3L,
        0x86ace348f355aadbL, 0x3582aff6f0f2f5b4L, 0x7228d51f5b150a80L,
        0xc10699a158b255efL, 0xfd7c20cc0cdaf4e8L, 0x4e526c720f7dab87L,
        0x09f8169ba49a54b3L, 0xbad65a25a73d0bdcL, 0x710d64410c4b16bdL,
        0xc22328ff0fec49d2L, 0x85895216a40bb6e6L, 0x36a71ea8a7ace989L,
        0x0adda7c5f3c4488eL, 0xb9f3eb7bf06317e1L, 0xfe5991925b84e8d5L,
        0x4d77dd2c5823b7baL, 0x64b62bcaebc387a1L, 0xd7986774e864d8ceL,
        0x90321d9d438327faL, 0x231c512340247895L, 0x1f66e84e144cd992L,
        0xac48a4f017eb86fdL, 0xebe2de19bc0c79c9L, 0x58cc92a7bfab26a6L,
        0x9317acc314dd3bc7L, 0x2039e07d177a64a8L, 0x67939a94bc9d9b9cL,
        0xd4bdd62abf3ac4f3L, 0xe8c76f47eb5265f4L, 0x5be923f9e8f53a9bL,
        0x1c4359104312c5afL, 0xaf6d15ae40b59ac0L, 0x192d8af2baf0e1e8L,
        0xaa03c64cb957be87L, 0xeda9bca512b041b3L, 0x5e87f01b11171edcL,
        0x62fd4976457fbfdbL, 0xd1d305c846d8e0b4L, 0x96797f21ed3f1f80L,
        0x2557339fee9840efL, 0xee8c0dfb45ee5d8eL, 0x5da24145464902e1L,
        0x1a083bacedaefdd5L, 0xa9267712ee09a2baL, 0x955cce7fba6103bdL,
        0x267282c1b9c65cd2L, 0x61d8f8281221a3e6L, 0xd2f6b4961186fc89L,
        0x9f8169ba49a54b33L, 0x2caf25044a02145cL, 0x6b055fede1e5eb68L,
        0xd82b1353e242b407L, 0xe451aa3eb62a1500L, 0x577fe680b58d4a6fL,
        0x10d59c691e6ab55bL, 0xa3fbd0d71dcdea34L, 0x6820eeb3b6bbf755L,
        0xdb0ea20db51ca83aL, 0x9ca4d8e41efb570eL, 0x2f8a945a1d5c0861L,
        0x13f02d374934a966L, 0xa0de61894a93f609L, 0xe7741b60e174093dL,
        0x545a57dee2d35652L, 0xe21ac88218962d7aL, 0x5134843c1b317215L,
        0x169efed5b0d68d21L, 0xa5b0b26bb371d24eL, 0x99ca0b06e7197349L,
        0x2ae447b8e4be2c26L, 0x6d4e3d514f59d312L, 0xde6071ef4cfe8c7dL,
        0x15bb4f8be788911cL, 0xa6950335e42fce73L, 0xe13f79dc4fc83147L,
        0x521135624c6f6e28L, 0x6e6b8c0f1807cf2fL, 0xdd45c0b11ba09040L,
        0x9aefba58b0476f74L, 0x29c1f6e6b3e0301bL, 0xc96c5795d7870f42L,
        0x7a421b2bd420502dL, 0x3de861c27fc7af19L, 0x8ec62d7c7c60f076L,
        0xb2bc941128085171L, 0x0192d8af2baf0e1eL, 0x4638a2468048f12aL,
        0xf516eef883efae45L, 0x3ecdd09c2899b324L, 0x8de39c222b3eec4bL,
        0xca49e6cb80d9137fL, 0x7967aa75837e4c10L, 0x451d1318d716ed17L,
        0xf6335fa6d4b1b278L, 0xb199254f7f564d4cL, 0x02b769f17cf11223L,
        0xb4f7f6ad86b4690bL, 0x07d9ba1385133664L, 0x4073c0fa2ef4c950L,
        0xf35d8c442d53963fL, 0xcf273529793b3738L, 0x7c0979977a9c6857L,
        0x3ba3037ed17b9763L, 0x888d4fc0d2dcc80cL, 0x435671a479aad56dL,
        0xf0783d1a7a0d8a02L, 0xb7d247f3d1ea7536L, 0x04fc0b4dd24d2a59L,
        0x3886b22086258b5eL, 0x8ba8fe9e8582d431L, 0xcc0284772e652b05L,
        0x7f2cc8c92dc2746aL, 0x325b15e575e1c3d0L, 0x8175595b76469cbfL,
        0xc6df23b2dda1638bL, 0x75f16f0cde063ce4L, 0x498bd6618a6e9de3L,
        0xfaa59adf89c9c28cL, 0xbd0fe036222e3db8L, 0x0e21ac88218962d7L,
        0xc5fa92ec8aff7fb6L, 0x76d4de52895820d9L, 0x317ea4bb22bfdfedL,
        0x8250e80521188082L, 0xbe2a516875702185L, 0x0d041dd676d77eeaL,
        0x4aae673fdd3081deL, 0xf9802b81de97deb1L, 0x4fc0b4dd24d2a599L,
        0xfceef8632775faf6L, 0xbb44828a8c9205c2L, 0x086ace348f355aadL,
        0x34107759db5dfbaaL, 0x873e3be7d8faa4c5L, 0xc094410e731d5bf1L,
        0x73ba0db070ba049eL, 0xb86133d4dbcc19ffL, 0x0b4f7f6ad86b4690L,
        0x4ce50583738cb9a4L, 0xffcb493d702be6cbL, 0xc3b1f050244347ccL,
        0x709fbcee27e418a3L, 0x3735c6078c03e797L, 0x841b8ab98fa4b8f8L,
        0xadda7c5f3c4488e3L, 0x1ef430e13fe3d78cL, 0x595e4a08940428b8L,
        0xea7006b697a377d7L, 0xd60abfdbc3cbd6d0L, 0x6524f365c06c89bfL,
        0x228e898c6b8b768bL, 0x91a0c532682c29e4L, 0x5a7bfb56c35a3485L,
        0xe955b7e8c0fd6beaL, 0xaeffcd016b1a94deL, 0x1dd181bf68bdcbb1L,
        0x21ab38d23cd56ab6L, 0x9285746c3f7235d9L, 0xd52f0e859495caedL,
        0x6601423b97329582L, 0xd041dd676d77eeaaL, 0x636f91d96ed0b1c5L,
        0x24c5eb30c5374ef1L, 0x97eba78ec690119eL, 0xab911ee392f8b099L,
        0x18bf525d915feff6L, 0x5f1528b43ab810c2L, 0xec3b640a391f4fadL,
        0x27e05a6e926952ccL, 0x94ce16d091ce0da3L, 0xd3646c393a29f297L,
        0x604a2087398eadf8L, 0x5c3099ea6de60cffL, 0xef1ed5546e415390L,
        0xa8b4afbdc5a6aca4L, 0x1b9ae303c601f3cbL, 0x56ed3e2f9e224471L,
        0xe5c372919d851b1eL, 0xa26908783662e42aL, 0x114744c635c5bb45L,
        0x2d3dfdab61ad1a42L, 0x9e13b115620a452dL, 0xd9b9cbfcc9edba19L,
        0x6a978742ca4ae576L, 0xa14cb926613cf817L, 0x1262f598629ba778L,
        0x55c88f71c97c584cL, 0xe6e6c3cfcadb0723L, 0xda9c7aa29eb3a624L,
        0x69b2361c9d14f94bL, 0x2e184cf536f3067fL, 0x9d36004b35545910L,
        0x2b769f17cf112238L, 0x9858d3a9ccb67d57L, 0xdff2a94067518263L,
        0x6cdce5fe64f6dd0cL, 0x50a65c93309e7c0bL, 0xe388102d33392364L,
        0xa4226ac498dedc50L, 0x170c267a9b79833fL, 0xdcd7181e300f9e5eL,
        0x6ff954a033a8c131L, 0x28532e49984f3e05L, 0x9b7d62f79be8616aL,
        0xa707db9acf80c06dL, 0x14299724cc279f02L, 0x5383edcd67c06036L,
        0xe0ada17364673f59L
    };
    /* ECMA-182 */
    private final static long POLY = 0xc96c5795d7870f42L;
    /* Current CRC value. */
    private long value;

    public Crc64() {
        this(0L);
    }

    public Crc64(long value) {
        this.value = value;
    }

    public Crc64(byte[] bytes, int len) {
        this.value = 0;
        update(bytes, len);
    }

    /**
     * Construct new CRC64 instance from byte array.
     **/
    public static Crc64 fromBytes(byte[] bytes) {
        long value = 0;
        for (int idx = 0; idx < 4; idx++) {
            value <<= 8;
            value ^= (long) bytes[idx] & 0xFF;
        }
        return new Crc64(value);
    }

    /**
     * Get 8 byte representation of current CRC64 value.
     **/
    public byte[] bytes() {
        byte[] bytes = new byte[8];
        for (int idx = 0; idx < 8; idx++) {
            bytes[7 - idx] = (byte) (this.value >>> (idx * 8));
        }
        return bytes;
    }

    /**
     * Get long representation of current CRC64 value.
     **/
    @Override
    public long value() {
        return this.value;
    }

    /**
     * Update CRC64 with new byte block.
     **/
    public void update(byte[] bytes, int len) {
        int idx = 0;
        this.value = ~this.value;
        while (len > 0) {
            this.value = TABLE[((int) (this.value ^ bytes[idx])) & 0xff]
                ^ (this.value >>> 8);
            idx++;
            len--;
        }
        this.value = ~this.value;
    }
}